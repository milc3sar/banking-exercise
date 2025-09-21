package com.milcesar.msclientes.domain.event;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

public record ClienteActualizado(UUID clienteId, boolean estado, Instant occurredAt)
    implements Serializable {
  @Serial private static final long serialVersionUID = 1L;
}
