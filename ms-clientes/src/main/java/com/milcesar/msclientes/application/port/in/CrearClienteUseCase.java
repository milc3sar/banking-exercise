package com.milcesar.msclientes.application.port.in;

import com.milcesar.msclientes.application.command.CrearClienteCommand;
import com.milcesar.msclientes.application.dto.ClienteDTO;

public interface CrearClienteUseCase {
  ClienteDTO crear(CrearClienteCommand cmd);
}
