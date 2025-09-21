package com.milcesar.mscuentas.application.port.out;

import com.milcesar.mscuentas.domain.model.Movimiento;

public interface GuardarMovimientoPort {
  Movimiento save(Movimiento m);
}
