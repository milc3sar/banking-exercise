package com.milcesar.mscuentas.application.command;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CrearCuentaCommand {
  @NotBlank private String numeroCuenta;
  @NotBlank private String tipo;

  @NotNull
  @DecimalMin("0.00")
  private BigDecimal saldoInicial;

  @NotNull private UUID clienteId;
}
