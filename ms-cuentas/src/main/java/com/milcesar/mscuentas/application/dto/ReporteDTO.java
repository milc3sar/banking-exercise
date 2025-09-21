package com.milcesar.mscuentas.application.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReporteDTO {
  private UUID clienteId;
  private Instant desde;
  private Instant hasta;
  private List<CuentaConMovs> cuentas;

  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class CuentaConMovs {
    private String numeroCuenta;
    private String tipo;
    private BigDecimal saldoActual;
    private List<MovimientoDTO> movimientos;
  }
}
