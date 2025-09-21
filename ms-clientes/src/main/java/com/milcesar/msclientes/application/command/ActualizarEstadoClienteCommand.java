package com.milcesar.msclientes.application.command;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActualizarEstadoClienteCommand {
  @NotNull private UUID clienteId;
  @NotNull private Boolean estado;
}
