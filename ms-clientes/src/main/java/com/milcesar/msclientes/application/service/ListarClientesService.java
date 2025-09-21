package com.milcesar.msclientes.application.service;

import com.milcesar.msclientes.application.dto.ClienteDTO;
import com.milcesar.msclientes.application.port.in.ListarClientesUseCase;
import com.milcesar.msclientes.application.port.out.ListarClientesReadPort;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ListarClientesService implements ListarClientesUseCase {

  private final ListarClientesReadPort readPort;

  @Override
  public List<ClienteDTO> listar(String identificacionLike, int page, int size) {
    var pageReq = PageRequest.of(page, size);
    var pageRes = readPort.listarPorIdentificacion(identificacionLike, pageReq);
    return pageRes
        .map(
            v ->
                ClienteDTO.builder()
                    .clienteId(v.clienteId())
                    .nombre(v.nombre())
                    .identificacion(v.identificacion())
                    .estado(v.estado())
                    .build())
        .getContent();
  }
}
