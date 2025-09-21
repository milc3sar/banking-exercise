package com.milcesar.mscuentas.infrastructure.adapter.out.persistence.jpa.repository;

import com.milcesar.mscuentas.infrastructure.adapter.out.persistence.jpa.entity.MovimientoEntity;
import java.time.Instant;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataMovimientoRepository extends JpaRepository<MovimientoEntity, Long> {
  List<MovimientoEntity> findByNumeroCuentaAndFechaBetweenOrderByFechaAsc(
      String numeroCuenta, Instant desde, Instant hasta);
}
