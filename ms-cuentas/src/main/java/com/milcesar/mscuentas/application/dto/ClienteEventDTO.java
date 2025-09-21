package com.milcesar.mscuentas.application.dto;

import java.time.Instant;
import java.util.UUID;
import lombok.Data;

@Data
public class ClienteEventDTO {
  private UUID clienteId;
  private String nombre;
  private boolean estado;
  private Instant occurredAt;
}
