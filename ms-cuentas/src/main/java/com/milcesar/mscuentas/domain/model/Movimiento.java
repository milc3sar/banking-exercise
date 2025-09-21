package com.milcesar.mscuentas.domain.model;

import java.math.BigDecimal;
import java.time.Instant;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Movimiento {
  private Long id;
  private Instant fecha;
  private TipoMovimiento tipo;
  private BigDecimal valor;
  private BigDecimal saldoPosterior;
  private String numeroCuenta;
}
