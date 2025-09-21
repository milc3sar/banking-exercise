package com.milcesar.mscuentas.infrastructure.adapter.out.persistence.jpa.repository;

import com.milcesar.mscuentas.infrastructure.adapter.out.persistence.jpa.entity.ClienteCacheEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataClienteCacheRepository extends JpaRepository<ClienteCacheEntity, UUID> {}
