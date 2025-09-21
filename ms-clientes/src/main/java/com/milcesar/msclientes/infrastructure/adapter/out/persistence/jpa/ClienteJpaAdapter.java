package com.milcesar.msclientes.infrastructure.adapter.out.persistence.jpa;

import com.milcesar.msclientes.application.port.out.PersistirClientePort;
import com.milcesar.msclientes.application.port.out.PersistirPersonaPort;
import com.milcesar.msclientes.domain.model.Cliente;
import com.milcesar.msclientes.domain.model.Persona;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClienteJpaAdapter implements PersistirClientePort, PersistirPersonaPort {

  private final SpringDataPersonaRepository personaRepo;
  private final SpringDataClienteRepository clienteRepo;
  private final JpaMappers mappers;

  // Persona
  @Override
  public Persona save(Persona p) {
    return mappers.toDomain(personaRepo.save(mappers.toEntity(p)));
  }

  @Override
  public Optional<Persona> findByIdentificacion(String id) {
    return personaRepo.findByIdentificacion(id).map(mappers::toDomain);
  }

  @Override
  public Optional<Persona> findById(Long id) {
    return personaRepo.findById(id).map(mappers::toDomain);
  }

  @Override
  public Cliente save(Cliente c) {
    return mappers.toDomain(clienteRepo.save(mappers.toEntity(c)));
  }

  @Override
  public Optional<Cliente> findByClienteId(UUID clienteId) {
    return clienteRepo.findByClienteId(clienteId).map(mappers::toDomain);
  }

  @Override
  public List<Cliente> findAllByIdentificacionLike(String pattern, int page, int size) {
    var persons = personaRepo.findAll(PageRequest.of(page, size)).getContent();
    var idByPersona = new HashMap<Long, Persona>();
    persons.forEach(p -> idByPersona.put(p.getId(), mappers.toDomain(p)));
    var clientes = clienteRepo.findAll(PageRequest.of(page, size)).getContent();
    return clientes.stream().map(mappers::toDomain).toList();
  }
}
