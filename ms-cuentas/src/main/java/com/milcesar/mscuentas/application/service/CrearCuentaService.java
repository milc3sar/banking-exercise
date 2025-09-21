package com.milcesar.mscuentas.application.service;

import com.milcesar.mscuentas.application.command.CrearCuentaCommand;
import com.milcesar.mscuentas.application.dto.CuentaDTO;
import com.milcesar.mscuentas.application.port.in.CrearCuentaUseCase;
import com.milcesar.mscuentas.application.port.out.ClienteQueryPort;
import com.milcesar.mscuentas.application.port.out.GuardarCuentaPort;
import com.milcesar.mscuentas.domain.exception.BusinessRuleException;
import com.milcesar.mscuentas.domain.model.Cuenta;
import com.milcesar.mscuentas.domain.model.TipoCuenta;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class CrearCuentaService implements CrearCuentaUseCase {
  private final GuardarCuentaPort guardarCuenta;
  private final ClienteQueryPort clienteQuery;

  @Override
  public CuentaDTO crear(CrearCuentaCommand command) {
    if (!clienteQuery.clienteActivo(command.getClienteId())) {
      throw new BusinessRuleException("Cliente no activo");
    }
    var cuenta =
        Cuenta.builder()
            .numeroCuenta(command.getNumeroCuenta())
            .tipo(TipoCuenta.valueOf(command.getTipo()))
            .saldo(command.getSaldoInicial() == null ? BigDecimal.ZERO : command.getSaldoInicial())
            .estado(true)
            .clienteId(command.getClienteId())
            .build();

    var saved = guardarCuenta.save(cuenta);
    return CuentaDTO.builder()
        .numeroCuenta(saved.getNumeroCuenta())
        .tipo(saved.getTipo().name())
        .saldo(saved.getSaldo())
        .estado(saved.isEstado())
        .clienteId(saved.getClienteId())
        .build();
  }
}
