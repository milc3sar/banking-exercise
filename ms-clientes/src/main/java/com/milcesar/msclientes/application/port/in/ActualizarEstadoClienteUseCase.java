package com.milcesar.msclientes.application.port.in;

import com.milcesar.msclientes.application.command.ActualizarEstadoClienteCommand;
import com.milcesar.msclientes.application.dto.ClienteDTO;

public interface ActualizarEstadoClienteUseCase {
  ClienteDTO actualizarEstado(ActualizarEstadoClienteCommand cmd);
}
