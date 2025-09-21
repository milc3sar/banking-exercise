package com.milcesar.msclientes.application.port.out;

import com.milcesar.msclientes.domain.event.ClienteActualizado;
import com.milcesar.msclientes.domain.event.ClienteCreado;

public interface PublicarClientePort {
  void publishCreado(ClienteCreado evt);

  void publishActualizado(ClienteActualizado evt);
}
