package com.milcesar.mscuentas.application.port.out;

import java.util.UUID;

public interface ClienteQueryPort {
  boolean clienteActivo(UUID clienteId);
}
