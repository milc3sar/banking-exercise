package com.milcesar.msclientes.infrastructure.adapter.out.persistence.jpa;

import com.milcesar.msclientes.infrastructure.adapter.out.persistence.jpa.entity.PersonaEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataPersonaRepository extends JpaRepository<PersonaEntity, Long> {
  Optional<PersonaEntity> findByIdentificacion(String identificacion);
}
