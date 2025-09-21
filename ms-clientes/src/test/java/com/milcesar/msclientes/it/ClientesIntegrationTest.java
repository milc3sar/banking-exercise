package com.milcesar.msclientes.it;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.milcesar.msclientes.application.command.CrearClienteCommand;
import com.milcesar.msclientes.application.dto.PersonaDTO;
import com.milcesar.msclientes.application.port.in.CrearClienteUseCase;
import com.milcesar.msclientes.application.port.out.PublicarClientePort;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
class ClientesIntegrationTest {

  @Container
  static PostgreSQLContainer<?> postgres =
      new PostgreSQLContainer<>("postgres:16")
          .withDatabaseName("clientesdb")
          .withUsername("app")
          .withPassword("app");
  @Autowired CrearClienteUseCase crearCliente;
  @SpyBean PublicarClientePort publisher;

  @DynamicPropertySource
  static void props(DynamicPropertyRegistry r) {
    r.add("spring.datasource.url", postgres::getJdbcUrl);
    r.add("spring.datasource.username", postgres::getUsername);
    r.add("spring.datasource.password", postgres::getPassword);
    r.add("spring.jpa.hibernate.ddl-auto", () -> "validate");
    r.add("spring.flyway.enabled", () -> "true");
  }

  @Test
  void crear_cliente_persiste_y_publica_evento() {
    var dto =
        crearCliente.crear(
            CrearClienteCommand.builder()
                .persona(PersonaDTO.builder().nombre("Luis").identificacion("DNI-77").build())
                .password("123456")
                .build());

    Assertions.assertNotNull(dto.getClienteId());
    verify(publisher, times(1)).publishCreado(org.mockito.ArgumentMatchers.any());
  }
}
