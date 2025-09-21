package com.milcesar.msclientes.domain.model;

import static org.junit.jupiter.api.Assertions.*;

import com.milcesar.msclientes.domain.exception.BusinessRuleException;
import org.junit.jupiter.api.Test;

class ClienteTest {
  @Test
  void setPasswordHash_no_debe_aceptar_vacio() {
    var c = Cliente.builder().build();
    assertThrows(BusinessRuleException.class, () -> c.setPasswordHash(""));
  }

  @Test
  void activar_inactivar_cambia_estado() {
    var c = Cliente.builder().estado(false).build();
    c.activar();
    assertTrue(c.isEstado());
    c.inactivar();
    assertFalse(c.isEstado());
  }
}
