package com.milcesar.msclientes.application.service;

import com.milcesar.msclientes.application.command.CrearClienteCommand;
import com.milcesar.msclientes.application.dto.ClienteDTO;
import com.milcesar.msclientes.application.port.in.CrearClienteUseCase;
import com.milcesar.msclientes.application.port.out.PersistirClientePort;
import com.milcesar.msclientes.application.port.out.PersistirPersonaPort;
import com.milcesar.msclientes.application.port.out.PublicarClientePort;
import com.milcesar.msclientes.domain.event.ClienteCreado;
import com.milcesar.msclientes.domain.model.Cliente;
import com.milcesar.msclientes.domain.model.Persona;
import java.time.Instant;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CrearClienteService implements CrearClienteUseCase {
  private final PersistirPersonaPort personaPort;
  private final PersistirClientePort clientePort;
  private final PublicarClientePort publisher;

  @Override
  public ClienteDTO crear(CrearClienteCommand cmd) {
    var pDto = cmd.getPersona();
    var persona =
        Persona.builder()
            .nombre(pDto.getNombre())
            .genero(pDto.getGenero())
            .edad(pDto.getEdad())
            .identificacion(pDto.getIdentificacion())
            .direccion(pDto.getDireccion())
            .telefono(pDto.getTelefono())
            .build();

    var personaPersistida =
        personaPort
            .findByIdentificacion(persona.getIdentificacion())
            .orElseGet(() -> personaPort.save(persona));

    String hashedPassword = BCrypt.hashpw(cmd.getPassword(), BCrypt.gensalt());

    var cliente =
        Cliente.builder()
            .clienteId(UUID.randomUUID())
            .personaId(personaPersistida.getId())
            .passwordHash(hashedPassword)
            .estado(true)
            .build();

    var saved = clientePort.save(cliente);

    publisher.publishCreado(
        new ClienteCreado(
            saved.getClienteId(),
            personaPersistida.getId(),
            personaPersistida.getNombre(),
            saved.isEstado(),
            Instant.now()));

    return ClienteDTO.builder()
        .clienteId(saved.getClienteId())
        .nombre(personaPersistida.getNombre())
        .identificacion(personaPersistida.getIdentificacion())
        .estado(saved.isEstado())
        .build();
  }
}
