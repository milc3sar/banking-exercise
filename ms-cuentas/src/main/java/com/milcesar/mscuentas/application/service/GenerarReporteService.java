package com.milcesar.mscuentas.application.service;

import com.milcesar.mscuentas.application.command.GenerarReporteCommand;
import com.milcesar.mscuentas.application.dto.MovimientoDTO;
import com.milcesar.mscuentas.application.dto.ReporteDTO;
import com.milcesar.mscuentas.application.port.in.GenerarReporteUseCase;
import com.milcesar.mscuentas.application.port.out.ListarCuentasPorClientePort;
import com.milcesar.mscuentas.application.port.out.ListarMovimientosPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GenerarReporteService implements GenerarReporteUseCase {
  private final ListarCuentasPorClientePort cuentasPort;
  private final ListarMovimientosPort movsPort;

  @Override
  public ReporteDTO generar(GenerarReporteCommand cmd) {
    var cuentas = cuentasPort.findByClienteId(cmd.getClienteId());
    var cuentasOut =
        cuentas.stream()
            .map(
                c -> {
                  var movs =
                      movsPort.list(c.getNumeroCuenta(), cmd.getDesde(), cmd.getHasta()).stream()
                          .map(
                              m ->
                                  MovimientoDTO.builder()
                                      .fecha(m.getFecha())
                                      .tipo(m.getTipo().name())
                                      .valor(m.getValor())
                                      .saldoPosterior(m.getSaldoPosterior())
                                      .build())
                          .toList();
                  return new ReporteDTO.CuentaConMovs(
                      c.getNumeroCuenta(), c.getTipo().name(), c.getSaldo(), movs);
                })
            .toList();

    return ReporteDTO.builder()
        .clienteId(cmd.getClienteId())
        .desde(cmd.getDesde())
        .hasta(cmd.getHasta())
        .cuentas(cuentasOut)
        .build();
  }
}
