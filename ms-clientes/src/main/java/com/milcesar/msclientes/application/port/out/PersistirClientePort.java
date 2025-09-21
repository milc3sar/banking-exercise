package com.milcesar.msclientes.application.port.out;

import com.milcesar.msclientes.domain.model.Cliente;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PersistirClientePort {
  Cliente save(Cliente c);

  Optional<Cliente> findByClienteId(UUID clienteId);

  List<Cliente> findAllByIdentificacionLike(String pattern, int page, int size);
}
