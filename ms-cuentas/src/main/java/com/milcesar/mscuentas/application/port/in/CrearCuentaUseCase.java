package com.milcesar.mscuentas.application.port.in;

import com.milcesar.mscuentas.application.command.CrearCuentaCommand;
import com.milcesar.mscuentas.application.dto.CuentaDTO;

public interface CrearCuentaUseCase {
  CuentaDTO crear(CrearCuentaCommand command);
}
