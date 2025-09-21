package com.milcesar.mscuentas.infrastructure.adapter.out.persistence.jpa;

import com.milcesar.mscuentas.application.port.out.ClienteQueryPort;
import com.milcesar.mscuentas.infrastructure.adapter.out.persistence.jpa.entity.ClienteCacheEntity;
import com.milcesar.mscuentas.infrastructure.adapter.out.persistence.jpa.repository.SpringDataClienteCacheRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClienteCacheJpaAdapter implements ClienteQueryPort {
  private final SpringDataClienteCacheRepository repo;

  @Override
  public boolean clienteActivo(UUID clienteId) {
    return repo.findById(clienteId).map(ClienteCacheEntity::isEstado).orElse(false);
  }
}
