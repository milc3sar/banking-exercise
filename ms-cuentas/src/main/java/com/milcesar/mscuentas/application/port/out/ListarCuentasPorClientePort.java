package com.milcesar.mscuentas.application.port.out;

import com.milcesar.mscuentas.domain.model.Cuenta;
import java.util.List;
import java.util.UUID;

public interface ListarCuentasPorClientePort {
  List<Cuenta> findByClienteId(UUID clienteId);
}
