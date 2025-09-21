package com.milcesar.mscuentas.application.service;

import com.milcesar.mscuentas.application.command.RegistrarMovimientoCommand;
import com.milcesar.mscuentas.application.dto.MovimientoDTO;
import com.milcesar.mscuentas.application.port.in.RegistrarMovimientoUseCase;
import com.milcesar.mscuentas.application.port.out.*;
import com.milcesar.mscuentas.domain.event.MovimientoCreado;
import com.milcesar.mscuentas.domain.exception.BusinessRuleException;
import com.milcesar.mscuentas.domain.exception.NotFoundException;
import com.milcesar.mscuentas.domain.model.Movimiento;
import com.milcesar.mscuentas.domain.model.TipoMovimiento;
import jakarta.transaction.Transactional;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class RegistrarMovimientoService implements RegistrarMovimientoUseCase {
  private final CargarCuentaPort cargarCuenta;
  private final GuardarCuentaPort guardarCuenta;
  private final GuardarMovimientoPort guardarMovimiento;
  private final IdempotencyPort idem;
  private final PublicarMovimientoPort publisher;

  @Override
  public MovimientoDTO registrar(RegistrarMovimientoCommand command, String idemKey) {
    if (idem.exists(idemKey)) {
      throw new BusinessRuleException("Reintento detectado");
    }

    var cuenta =
        cargarCuenta
            .lockByNumero(command.getNumeroCuenta())
            .orElseThrow(() -> new NotFoundException("Cuenta no existe"));

    var tipo = TipoMovimiento.valueOf(command.getTipo());
    if (tipo == TipoMovimiento.RETIRO) cuenta.debitar(command.getValor());
    else cuenta.acreditar(command.getValor());

    guardarCuenta.save(cuenta);

    var mov =
        Movimiento.builder()
            .fecha(Instant.now())
            .tipo(tipo)
            .valor(tipo == TipoMovimiento.RETIRO ? command.getValor().negate() : command.getValor())
            .saldoPosterior(cuenta.getSaldo())
            .numeroCuenta(cuenta.getNumeroCuenta())
            .build();

    mov = guardarMovimiento.save(mov);

    idem.mark(idemKey);
    publisher.publish(
        new MovimientoCreado(
            mov.getNumeroCuenta(), mov.getValor(), mov.getSaldoPosterior(), mov.getFecha()));

    return MovimientoDTO.builder()
        .fecha(mov.getFecha())
        .tipo(mov.getTipo().name())
        .valor(mov.getValor())
        .saldoPosterior(mov.getSaldoPosterior())
        .build();
  }
}
