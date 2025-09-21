package com.milcesar.msclientes.application.port.out;

import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ListarClientesReadPort {
  Page<ClienteListado> listarPorIdentificacion(String pattern, Pageable pageable);

  record ClienteListado(UUID clienteId, String nombre, String identificacion, boolean estado) {}
}
