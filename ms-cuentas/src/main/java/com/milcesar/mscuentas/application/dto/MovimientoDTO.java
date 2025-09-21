package com.milcesar.mscuentas.application.dto;

import java.math.BigDecimal;
import java.time.Instant;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovimientoDTO {
  private Instant fecha;
  private String tipo;
  private BigDecimal valor;
  private BigDecimal saldoPosterior;
}
