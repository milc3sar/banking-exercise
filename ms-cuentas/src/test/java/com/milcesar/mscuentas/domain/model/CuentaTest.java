package com.milcesar.mscuentas.domain.model;

import static org.junit.jupiter.api.Assertions.*;

import com.milcesar.mscuentas.domain.exception.BusinessRuleException;
import java.math.BigDecimal;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class CuentaTest {
  @Test
  void debitar_deberia_fallar_por_saldo_insuficiente() {
    var c =
        Cuenta.builder()
            .numeroCuenta("123")
            .tipo(TipoCuenta.AHORROS)
            .saldo(new BigDecimal("100.00"))
            .estado(true)
            .clienteId(UUID.randomUUID())
            .build();

    var ex = assertThrows(BusinessRuleException.class, () -> c.debitar(new BigDecimal("150.00")));
    assertEquals("Saldo no disponible", ex.getMessage());
  }

  @Test
  void depositar_incrementa_saldo() {
    var c =
        Cuenta.builder()
            .numeroCuenta("123")
            .tipo(TipoCuenta.AHORROS)
            .saldo(new BigDecimal("100.00"))
            .estado(true)
            .clienteId(UUID.randomUUID())
            .build();

    c.acreditar(new BigDecimal("40.00"));
    assertEquals(new BigDecimal("140.00"), c.getSaldo());
  }
}
