package com.milcesar.msclientes.infrastructure.adapter.out.persistence.jpa;

import com.milcesar.msclientes.application.port.out.ListarClientesReadPort;
import com.milcesar.msclientes.infrastructure.adapter.out.persistence.jpa.projection.ClienteListadoProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClienteListadoJpaAdapter implements ListarClientesReadPort {

  private final SpringDataClienteListadoRepository repo;

  private static String blankToNull(String s) {
    return (s == null || s.isBlank()) ? null : s;
  }

  @Override
  public Page<ClienteListado> listarPorIdentificacion(String pattern, Pageable pageable) {
    var page = repo.listarPorIdentificacion(blankToNull(pattern), pageable);
    return page.map(this::toReadModel);
  }

  private ClienteListado toReadModel(ClienteListadoProjection p) {
    return new ClienteListado(
        p.getClienteId(), p.getNombre(), p.getIdentificacion(), p.getEstado());
  }
}
