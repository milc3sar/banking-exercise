package com.milcesar.mscuentas.infrastructure.adapter.in.web;

import com.milcesar.mscuentas.application.command.CrearCuentaCommand;
import com.milcesar.mscuentas.application.dto.CuentaDTO;
import com.milcesar.mscuentas.application.port.in.CrearCuentaUseCase;
import com.milcesar.mscuentas.application.port.out.CargarCuentaPort;
import com.milcesar.mscuentas.application.port.out.GuardarCuentaPort;
import com.milcesar.mscuentas.domain.exception.NotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cuentas")
@RequiredArgsConstructor
public class CuentaController {
  private final CrearCuentaUseCase crearCuenta;
  private final CargarCuentaPort cargarCuenta;
  private final GuardarCuentaPort guardarCuenta;

  @PostMapping
  public ResponseEntity<CuentaDTO> crear(@Valid @RequestBody CrearCuentaCommand cmd) {
    return ResponseEntity.status(HttpStatus.CREATED).body(crearCuenta.crear(cmd));
  }

  @GetMapping("/{numero}")
  public ResponseEntity<CuentaDTO> obtener(@PathVariable String numero) {
    var c =
        cargarCuenta
            .lockByNumero(numero)
            .orElseThrow(() -> new NotFoundException("Cuenta no existe"));
    var dto =
        CuentaDTO.builder()
            .numeroCuenta(c.getNumeroCuenta())
            .tipo(c.getTipo().name())
            .saldo(c.getSaldo())
            .estado(c.isEstado())
            .clienteId(c.getClienteId())
            .build();
    return ResponseEntity.ok(dto);
  }

  @PutMapping("/{numero}")
  public ResponseEntity<CuentaDTO> actualizar(
      @PathVariable String numero, @Valid @RequestBody CuentaDTO dto) {
    var c =
        cargarCuenta
            .lockByNumero(numero)
            .orElseThrow(() -> new NotFoundException("Cuenta no existe"));
    c.setEstado(Boolean.TRUE.equals(dto.getEstado()));
    var saved = guardarCuenta.save(c);
    var out =
        CuentaDTO.builder()
            .numeroCuenta(saved.getNumeroCuenta())
            .tipo(saved.getTipo().name())
            .saldo(saved.getSaldo())
            .estado(saved.isEstado())
            .clienteId(saved.getClienteId())
            .build();
    return ResponseEntity.ok(out);
  }
}
