package com.milcesar.msclientes.infrastructure.adapter.out.persistence.jpa;

import com.milcesar.msclientes.infrastructure.adapter.out.persistence.jpa.entity.ClienteEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataClienteRepository extends JpaRepository<ClienteEntity, Long> {
  Optional<ClienteEntity> findByClienteId(UUID clienteId);
}
