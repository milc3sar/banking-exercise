package com.milcesar.mscuentas.application.port.out;

import com.milcesar.mscuentas.domain.model.Cuenta;
import java.util.Optional;

public interface CargarCuentaPort {
  Optional<Cuenta> lockByNumero(String numeroCuenta);
}
