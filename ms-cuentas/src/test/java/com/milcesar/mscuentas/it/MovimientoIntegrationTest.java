package com.milcesar.mscuentas.it;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.milcesar.mscuentas.application.command.RegistrarMovimientoCommand;
import com.milcesar.mscuentas.application.port.in.RegistrarMovimientoUseCase;
import com.milcesar.mscuentas.application.port.out.GuardarCuentaPort;
import com.milcesar.mscuentas.domain.exception.BusinessRuleException;
import com.milcesar.mscuentas.domain.model.Cuenta;
import com.milcesar.mscuentas.domain.model.TipoCuenta;
import java.math.BigDecimal;
import java.util.UUID;

import com.milcesar.mscuentas.infrastructure.adapter.out.persistence.jpa.CuentaJpaAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
@Transactional
class MovimientoIntegrationTest {

  @SuppressWarnings("resource")
  @Container
  static PostgreSQLContainer<?> postgres =
      new PostgreSQLContainer<>("postgres:16")
          .withDatabaseName("cuentasdb")
          .withUsername("app")
          .withPassword("app");
  @Autowired GuardarCuentaPort guardarCuenta;
  @Autowired RegistrarMovimientoUseCase registrar;
  @Autowired CuentaJpaAdapter cuentaJpaAdapter;

  @DynamicPropertySource
  static void props(DynamicPropertyRegistry r) {
    r.add("spring.datasource.url", postgres::getJdbcUrl);
    r.add("spring.datasource.username", postgres::getUsername);
    r.add("spring.datasource.password", postgres::getPassword);
    r.add("spring.jpa.hibernate.ddl-auto", () -> "validate");
    r.add("spring.flyway.enabled", () -> "true");
  }

  @BeforeEach
  void seed() {
    cuentaJpaAdapter.deleteByNumeroCuenta("ACC-001");
    var cuenta =
        Cuenta.builder()
            .numeroCuenta("ACC-001")
            .tipo(TipoCuenta.AHORROS)
            .saldo(new BigDecimal("200.00"))
            .estado(true)
            .clienteId(UUID.randomUUID())
            .build();
    guardarCuenta.save(cuenta);
  }

  @Test
  void retiro_insuficiente_responde_error() {
    var cmd =
        RegistrarMovimientoCommand.builder()
            .numeroCuenta("ACC-001")
            .tipo("RETIRO")
            .valor(new BigDecimal("500.00"))
            .build();

    var ex =
        assertThrows(
            BusinessRuleException.class,
            () -> registrar.registrar(cmd, UUID.randomUUID().toString()));
    assertEquals("Saldo no disponible", ex.getMessage());
  }

  @Test
  void deposito_y_retiro_actualizan_saldo() {
    registrar.registrar(
        RegistrarMovimientoCommand.builder()
            .numeroCuenta("ACC-001")
            .tipo("DEPOSITO")
            .valor(new BigDecimal("50.00"))
            .build(),
        UUID.randomUUID().toString());

    var mov2 =
        registrar.registrar(
            RegistrarMovimientoCommand.builder()
                .numeroCuenta("ACC-001")
                .tipo("RETIRO")
                .valor(new BigDecimal("20.00"))
                .build(),
            UUID.randomUUID().toString());

    assertEquals(new BigDecimal("230.00"), mov2.getSaldoPosterior()); // 200 + 50 - 20
  }
}
