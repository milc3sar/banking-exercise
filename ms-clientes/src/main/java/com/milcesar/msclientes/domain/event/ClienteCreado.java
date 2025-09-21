package com.milcesar.msclientes.domain.event;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

public record ClienteCreado(
    UUID clienteId, Long personaId, String nombre, boolean estado, Instant occurredAt)
    implements Serializable {
  @Serial private static final long serialVersionUID = 1L;
}
