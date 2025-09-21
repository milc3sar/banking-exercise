package com.milcesar.msclientes.application.port.in;

import com.milcesar.msclientes.application.dto.ClienteDTO;
import java.util.UUID;

public interface ObtenerClienteUseCase {
  ClienteDTO obtenerPorClienteId(UUID clienteId);
}
