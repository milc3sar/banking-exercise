package com.milcesar.mscuentas.application.command;

import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.util.UUID;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GenerarReporteCommand {
  @NotNull private UUID clienteId;
  @NotNull private Instant desde;
  @NotNull private Instant hasta;
}
