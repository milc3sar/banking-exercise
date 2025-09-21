package com.milcesar.mscuentas.e2e;

import static org.junit.jupiter.api.Assertions.*;

import com.milcesar.mscuentas.application.command.CrearCuentaCommand;
import com.milcesar.mscuentas.application.dto.CuentaDTO;
import com.milcesar.mscuentas.application.port.in.CrearCuentaUseCase;
import com.milcesar.mscuentas.domain.exception.BusinessRuleException;
import com.milcesar.mscuentas.infrastructure.adapter.in.messaging.amqp.ClientesAmqpConfig;
import com.milcesar.mscuentas.infrastructure.adapter.out.persistence.jpa.repository.SpringDataClienteCacheRepository;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.org.awaitility.Awaitility;

@SpringBootTest
@Testcontainers
class CuentasE2ETest {

  // Postgres real
  @Container
  static PostgreSQLContainer<?> postgres =
      new PostgreSQLContainer<>("postgres:16")
          .withDatabaseName("cuentasdb")
          .withUsername("app")
          .withPassword("app");

  // RabbitMQ real
  @Container static RabbitMQContainer rabbit = new RabbitMQContainer("rabbitmq:3.13-management");
  @Autowired RabbitTemplate rabbitTemplate;
  @Autowired SpringDataClienteCacheRepository cacheRepo;
  @Autowired CrearCuentaUseCase crearCuenta;

  @DynamicPropertySource
  static void props(DynamicPropertyRegistry r) {
    // DB
    r.add("spring.datasource.url", postgres::getJdbcUrl);
    r.add("spring.datasource.username", postgres::getUsername);
    r.add("spring.datasource.password", postgres::getPassword);
    r.add("spring.jpa.hibernate.ddl-auto", () -> "validate");
    r.add("spring.flyway.enabled", () -> "true");

    // Rabbit
    r.add("spring.rabbitmq.host", rabbit::getHost);
    r.add("spring.rabbitmq.port", rabbit::getAmqpPort);
    r.add("spring.rabbitmq.username", rabbit::getAdminUsername);
    r.add("spring.rabbitmq.password", rabbit::getAdminPassword);
  }

  @Test
  void debe_rechazar_creacion_cuenta_si_no_hay_evento_cliente() {
    var clienteId = UUID.randomUUID(); // no está en cache
    var cmd =
        CrearCuentaCommand.builder()
            .numeroCuenta("E2E-001")
            .tipo("AHORROS")
            .saldoInicial(new BigDecimal("100.00"))
            .clienteId(clienteId)
            .build();

    var ex = assertThrows(BusinessRuleException.class, () -> crearCuenta.crear(cmd));
    assertEquals("Cliente no activo", ex.getMessage());
  }

  @Test
  void debe_permitir_creacion_cuenta_tras_cliente_creado_y_bloquear_luego_si_se_inactiva() {
    var clienteId = UUID.randomUUID();

    // 1) Publica cliente.creado (activo = true)
    var creado = new ClienteCreado(clienteId, 10L, "Ana", true, Instant.now());
    rabbitTemplate.convertAndSend(ClientesAmqpConfig.EXCHANGE_CLIENTES, "cliente.creado", creado);

    // 2) Espera a que la cache se actualice (eventual consistency)
    Awaitility.await()
        .atMost(Duration.ofSeconds(5))
        .until(() -> cacheRepo.findById(clienteId).map(c -> c.isEstado()).orElse(false));

    // 3) Crea cuenta exitosamente
    CuentaDTO dto =
        crearCuenta.crear(
            CrearCuentaCommand.builder()
                .numeroCuenta("E2E-AC-1")
                .tipo("AHORROS")
                .saldoInicial(new BigDecimal("50.00"))
                .clienteId(clienteId)
                .build());

    assertEquals("E2E-AC-1", dto.getNumeroCuenta());
    assertTrue(dto.getEstado());

    // 4) Publica cliente.actualizado con estado = false
    var actualizado = new ClienteActualizado(clienteId, false, Instant.now());
    rabbitTemplate.convertAndSend(
        ClientesAmqpConfig.EXCHANGE_CLIENTES, "cliente.actualizado", actualizado);

    // 5) Espera propagación a cache
    Awaitility.await()
        .atMost(Duration.ofSeconds(5))
        .until(() -> cacheRepo.findById(clienteId).map(c -> !c.isEstado()).orElse(false));

    // 6) Intentar crear otra cuenta ahora debe fallar
    var ex =
        assertThrows(
            BusinessRuleException.class,
            () ->
                crearCuenta.crear(
                    CrearCuentaCommand.builder()
                        .numeroCuenta("E2E-AC-2")
                        .tipo("AHORROS")
                        .saldoInicial(new BigDecimal("10.00"))
                        .clienteId(clienteId)
                        .build()));
    assertEquals("Cliente no activo", ex.getMessage());
  }

  // Records del evento tal como los consume ms-cuentas (listener)
  public record ClienteCreado(
      UUID clienteId, Long personaId, String nombre, boolean estado, Instant occurredAt) {}

  public record ClienteActualizado(UUID clienteId, boolean estado, Instant occurredAt) {}
}
