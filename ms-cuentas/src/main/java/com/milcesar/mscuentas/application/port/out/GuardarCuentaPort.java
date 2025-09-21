package com.milcesar.mscuentas.application.port.out;

import com.milcesar.mscuentas.domain.model.Cuenta;

public interface GuardarCuentaPort {
  Cuenta save(Cuenta c);
}
