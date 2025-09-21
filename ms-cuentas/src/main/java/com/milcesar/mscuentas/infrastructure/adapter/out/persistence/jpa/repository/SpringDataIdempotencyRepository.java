package com.milcesar.mscuentas.infrastructure.adapter.out.persistence.jpa.repository;

import com.milcesar.mscuentas.infrastructure.adapter.out.persistence.jpa.entity.IdempotencyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataIdempotencyRepository extends JpaRepository<IdempotencyEntity, String> {}
