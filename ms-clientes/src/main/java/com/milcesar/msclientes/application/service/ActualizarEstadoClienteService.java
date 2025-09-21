package com.milcesar.msclientes.application.service;

import com.milcesar.msclientes.application.command.ActualizarEstadoClienteCommand;
import com.milcesar.msclientes.application.dto.ClienteDTO;
import com.milcesar.msclientes.application.port.in.ActualizarEstadoClienteUseCase;
import com.milcesar.msclientes.application.port.out.PersistirClientePort;
import com.milcesar.msclientes.application.port.out.PersistirPersonaPort;
import com.milcesar.msclientes.application.port.out.PublicarClientePort;
import com.milcesar.msclientes.domain.event.ClienteActualizado;
import com.milcesar.msclientes.domain.exception.NotFoundException;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ActualizarEstadoClienteService implements ActualizarEstadoClienteUseCase {

  private final PersistirClientePort clientePort;
  private final PersistirPersonaPort personaPort;
  private final PublicarClientePort publisher;

  @Override
  public ClienteDTO actualizarEstado(ActualizarEstadoClienteCommand cmd) {
    var cliente =
        clientePort
            .findByClienteId(cmd.getClienteId())
            .orElseThrow(() -> new NotFoundException("Cliente no existe"));

    if (Boolean.TRUE.equals(cmd.getEstado())) cliente.activar();
    else cliente.inactivar();
    var saved = clientePort.save(cliente);

    publisher.publishActualizado(
        new ClienteActualizado(saved.getClienteId(), saved.isEstado(), Instant.now()));

    var persona = personaPort.findById(saved.getPersonaId()).orElse(null);

    return ClienteDTO.builder()
        .clienteId(saved.getClienteId())
        .nombre(persona != null ? persona.getNombre() : null)
        .identificacion(persona != null ? persona.getIdentificacion() : null)
        .estado(saved.isEstado())
        .build();
  }
}
