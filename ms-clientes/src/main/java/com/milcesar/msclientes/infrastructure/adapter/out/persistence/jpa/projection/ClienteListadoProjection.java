package com.milcesar.msclientes.infrastructure.adapter.out.persistence.jpa.projection;

import java.util.UUID;

public interface ClienteListadoProjection {
  UUID getClienteId();

  String getNombre();

  String getIdentificacion();

  boolean getEstado();
}
