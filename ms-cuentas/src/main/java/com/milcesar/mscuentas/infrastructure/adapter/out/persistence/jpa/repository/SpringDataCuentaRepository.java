package com.milcesar.mscuentas.infrastructure.adapter.out.persistence.jpa.repository;

import com.milcesar.mscuentas.infrastructure.adapter.out.persistence.jpa.entity.CuentaEntity;
import jakarta.persistence.LockModeType;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SpringDataCuentaRepository extends JpaRepository<CuentaEntity, UUID> {
  Optional<CuentaEntity> findByNumeroCuenta(String numeroCuenta);

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  @Query("select c from CuentaEntity c where c.numeroCuenta = :n")
  Optional<CuentaEntity> lockByNumero(@Param("n") String n);

  List<CuentaEntity> findByClienteId(UUID clienteId);

  void deleteByNumeroCuenta(String numeroCuenta);
}
