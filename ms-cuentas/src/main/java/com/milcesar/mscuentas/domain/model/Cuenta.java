package com.milcesar.mscuentas.domain.model;

import com.milcesar.mscuentas.domain.exception.BusinessRuleException;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cuenta {
  private Long id;
  private String numeroCuenta;
  private TipoCuenta tipo;
  private BigDecimal saldo;
  private boolean estado;
  private UUID clienteId;

  private static void requirePositivo(BigDecimal valor) {
    if (valor == null || valor.signum() <= 0)
      throw new BusinessRuleException("Valor debe ser positivo");
  }

  public void acreditar(BigDecimal valor) {
    validarEstado();
    requirePositivo(valor);
    saldo = saldo.add(valor);
  }

  public void debitar(BigDecimal valor) {
    validarEstado();
    requirePositivo(valor);
    var nuevo = saldo.subtract(valor);
    if (nuevo.compareTo(BigDecimal.ZERO) < 0)
      throw new BusinessRuleException("Saldo no disponible");
    saldo = nuevo;
  }

  private void validarEstado() {
    if (!estado) throw new BusinessRuleException("Cuenta inactiva");
  }
}
