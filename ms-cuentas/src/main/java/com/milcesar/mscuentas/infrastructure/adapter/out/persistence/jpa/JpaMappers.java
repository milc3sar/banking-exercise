package com.milcesar.mscuentas.infrastructure.adapter.out.persistence.jpa;

import com.milcesar.mscuentas.domain.model.Cuenta;
import com.milcesar.mscuentas.domain.model.Movimiento;
import com.milcesar.mscuentas.infrastructure.adapter.out.persistence.jpa.entity.CuentaEntity;
import com.milcesar.mscuentas.infrastructure.adapter.out.persistence.jpa.entity.MovimientoEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface JpaMappers {
  // Cuenta
  Cuenta toDomain(CuentaEntity e);

  CuentaEntity toEntity(Cuenta c);

  // Movimiento
  Movimiento toDomain(MovimientoEntity e);

  MovimientoEntity toEntity(Movimiento m);
}
