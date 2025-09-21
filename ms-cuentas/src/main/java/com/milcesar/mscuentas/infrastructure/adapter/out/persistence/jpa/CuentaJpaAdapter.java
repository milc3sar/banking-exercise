package com.milcesar.mscuentas.infrastructure.adapter.out.persistence.jpa;

import com.milcesar.mscuentas.application.port.out.CargarCuentaPort;
import com.milcesar.mscuentas.application.port.out.GuardarCuentaPort;
import com.milcesar.mscuentas.application.port.out.ListarCuentasPorClientePort;
import com.milcesar.mscuentas.domain.model.Cuenta;
import com.milcesar.mscuentas.infrastructure.adapter.out.persistence.jpa.repository.SpringDataCuentaRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class CuentaJpaAdapter
    implements CargarCuentaPort, GuardarCuentaPort, ListarCuentasPorClientePort {
  private final SpringDataCuentaRepository repository;
  private final JpaMappers mappers;

  @Override
  @Transactional
  public Optional<Cuenta> lockByNumero(String numeroCuenta) {
    return repository.lockByNumero(numeroCuenta).map(mappers::toDomain);
  }

  @Override
  public Cuenta save(Cuenta c) {
    return mappers.toDomain(repository.save(mappers.toEntity(c)));
  }

  @Override
  public List<Cuenta> findByClienteId(UUID clienteId) {
    return repository.findByClienteId(clienteId).stream().map(mappers::toDomain).toList();
  }

  public void deleteByNumeroCuenta(String numeroCuenta) {
    repository.deleteByNumeroCuenta(numeroCuenta);
  }
}
