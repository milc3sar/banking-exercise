package com.milcesar.mscuentas.infrastructure.adapter.out.persistence.jpa;

import com.milcesar.mscuentas.application.port.out.GuardarMovimientoPort;
import com.milcesar.mscuentas.application.port.out.ListarMovimientosPort;
import com.milcesar.mscuentas.domain.model.Movimiento;
import com.milcesar.mscuentas.infrastructure.adapter.out.persistence.jpa.repository.SpringDataMovimientoRepository;
import java.time.Instant;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MovimientoJpaAdapter implements GuardarMovimientoPort, ListarMovimientosPort {
  private final SpringDataMovimientoRepository repo;
  private final JpaMappers mappers;

  @Override
  public Movimiento save(Movimiento m) {
    return mappers.toDomain(repo.save(mappers.toEntity(m)));
  }

  @Override
  public List<Movimiento> list(String numeroCuenta, Instant desde, Instant hasta) {
    return repo
        .findByNumeroCuentaAndFechaBetweenOrderByFechaAsc(numeroCuenta, desde, hasta)
        .stream()
        .map(mappers::toDomain)
        .toList();
  }
}
