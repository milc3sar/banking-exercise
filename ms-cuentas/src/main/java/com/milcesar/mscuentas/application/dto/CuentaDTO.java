package com.milcesar.mscuentas.application.dto;

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
public class CuentaDTO {
  @NotBlank private String numeroCuenta;
  @NotBlank private String tipo;

  @NotNull
  @DecimalMin(value = "0.00")
  private BigDecimal saldo;

  @NotNull private Boolean estado;
  @NotNull private UUID clienteId;
}
