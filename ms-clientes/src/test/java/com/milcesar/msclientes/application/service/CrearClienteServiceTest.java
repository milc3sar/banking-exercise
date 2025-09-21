package com.milcesar.msclientes.application.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.milcesar.msclientes.application.command.CrearClienteCommand;
import com.milcesar.msclientes.application.dto.PersonaDTO;
import com.milcesar.msclientes.application.port.out.PersistirClientePort;
import com.milcesar.msclientes.application.port.out.PersistirPersonaPort;
import com.milcesar.msclientes.application.port.out.PublicarClientePort;
import com.milcesar.msclientes.domain.model.Cliente;
import com.milcesar.msclientes.domain.model.Persona;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;
import org.mockito.ArgumentCaptor;

class CrearClienteServiceTest {

  @Test
  void crear_cliente_publica_evento_y_hashea_password() {
    var personaPort = mock(PersistirPersonaPort.class);
    var clientePort = mock(PersistirClientePort.class);
    var publisher = mock(PublicarClientePort.class);

    when(BCrypt.hashpw("secret", BCrypt.gensalt())).thenReturn("hash$secret");
    when(personaPort.findByIdentificacion("DNI-1")).thenReturn(Optional.empty());
    when(personaPort.save(any(Persona.class)))
        .thenAnswer(
            a -> {
              var p = (Persona) a.getArgument(0);
              p.setId(10L);
              return p;
            });
    when(clientePort.save(any(Cliente.class)))
        .thenAnswer(
            a -> {
              var c = (Cliente) a.getArgument(0);
              c.setId(99L);
              c.setClienteId(UUID.randomUUID());
              return c;
            });

    var svc = new CrearClienteService(personaPort, clientePort, publisher);

    var cmd =
        CrearClienteCommand.builder()
            .persona(PersonaDTO.builder().nombre("Ana").identificacion("DNI-1").build())
            .password("secret")
            .build();

    var dto = svc.crear(cmd);

    assertNotNull(dto.getClienteId());
    assertEquals("Ana", dto.getNombre());

    var captor = ArgumentCaptor.forClass(Cliente.class);
    verify(clientePort).save(captor.capture());
    assertEquals("hash$secret", captor.getValue().getPasswordHash());

    verify(publisher, times(1)).publishCreado(any());
  }
}
