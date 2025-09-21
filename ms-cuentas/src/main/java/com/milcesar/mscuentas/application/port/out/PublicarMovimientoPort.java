package com.milcesar.mscuentas.application.port.out;

import com.milcesar.mscuentas.domain.event.MovimientoCreado;

public interface PublicarMovimientoPort {
  void publish(MovimientoCreado evt);
}
