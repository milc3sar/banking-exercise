package com.milcesar.mscuentas.application.port.out;

import com.milcesar.mscuentas.domain.model.Movimiento;
import java.time.Instant;
import java.util.List;

public interface ListarMovimientosPort {
  List<Movimiento> list(String numeroCuenta, Instant desde, Instant hasta);
}
