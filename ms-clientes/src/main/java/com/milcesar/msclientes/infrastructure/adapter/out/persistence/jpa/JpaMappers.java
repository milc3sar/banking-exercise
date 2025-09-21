package com.milcesar.msclientes.infrastructure.adapter.out.persistence.jpa;

import com.milcesar.msclientes.domain.model.Cliente;
import com.milcesar.msclientes.domain.model.Persona;
import com.milcesar.msclientes.infrastructure.adapter.out.persistence.jpa.entity.ClienteEntity;
import com.milcesar.msclientes.infrastructure.adapter.out.persistence.jpa.entity.PersonaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface JpaMappers {
  Persona toDomain(PersonaEntity e);

  PersonaEntity toEntity(Persona p);

  @Mapping(source = "passwordHash", target = "passwordHash")
  Cliente toDomain(ClienteEntity e);

  @Mapping(source = "passwordHash", target = "passwordHash")
  ClienteEntity toEntity(Cliente c);
}
