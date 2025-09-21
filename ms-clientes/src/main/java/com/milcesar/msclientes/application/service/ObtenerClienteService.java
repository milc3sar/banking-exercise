package com.milcesar.msclientes.application.service;

import com.milcesar.msclientes.application.dto.ClienteDTO;
import com.milcesar.msclientes.application.port.in.ObtenerClienteUseCase;
import com.milcesar.msclientes.application.port.out.PersistirClientePort;
import com.milcesar.msclientes.application.port.out.PersistirPersonaPort;
import com.milcesar.msclientes.domain.exception.NotFoundException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ObtenerClienteService implements ObtenerClienteUseCase {

  private final PersistirClientePort clientePort;
  private final PersistirPersonaPort personaPort;

  @Override
  public ClienteDTO obtenerPorClienteId(UUID clienteId) {
    var cliente =
        clientePort
            .findByClienteId(clienteId)
            .orElseThrow(() -> new NotFoundException("Cliente no existe"));
    var persona = personaPort.findById(cliente.getPersonaId()).orElse(null);

    return ClienteDTO.builder()
        .clienteId(cliente.getClienteId())
        .nombre(persona != null ? persona.getNombre() : null)
        .identificacion(persona != null ? persona.getIdentificacion() : null)
        .estado(cliente.isEstado())
        .build();
  }
}
