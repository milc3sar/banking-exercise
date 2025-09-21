package com.milcesar.mscuentas.application.port.in;

import com.milcesar.mscuentas.application.command.RegistrarMovimientoCommand;
import com.milcesar.mscuentas.application.dto.MovimientoDTO;

public interface RegistrarMovimientoUseCase {
  MovimientoDTO registrar(RegistrarMovimientoCommand command, String idempotencyKey);
}
