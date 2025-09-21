package com.milcesar.mscuentas.infrastructure.adapter.out.persistence.jpa;

import com.milcesar.mscuentas.application.port.out.IdempotencyPort;
import com.milcesar.mscuentas.infrastructure.adapter.out.persistence.jpa.entity.IdempotencyEntity;
import com.milcesar.mscuentas.infrastructure.adapter.out.persistence.jpa.repository.SpringDataIdempotencyRepository;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class IdempotencyJpaAdapter implements IdempotencyPort {
  private final SpringDataIdempotencyRepository repo;

  @Override
  public boolean exists(String key) {
    return repo.existsById(key);
  }

  @Override
  public void mark(String key) {
    repo.save(new IdempotencyEntity(key, Instant.now()));
  }
}
