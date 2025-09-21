package com.milcesar.mscuentas.application.port.in;

import com.milcesar.mscuentas.application.command.GenerarReporteCommand;
import com.milcesar.mscuentas.application.dto.ReporteDTO;

public interface GenerarReporteUseCase {
  ReporteDTO generar(GenerarReporteCommand command);
}
