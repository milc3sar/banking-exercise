package com.milcesar.msclientes.application.port.in;

import com.milcesar.msclientes.application.dto.ClienteDTO;
import java.util.List;

public interface ListarClientesUseCase {
  List<ClienteDTO> listar(String identificacionLike, int page, int size);
}
