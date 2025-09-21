package com.milcesar.mscuentas.infrastructure.adapter.in.web;

import com.milcesar.mscuentas.application.command.RegistrarMovimientoCommand;
import com.milcesar.mscuentas.application.dto.MovimientoDTO;
import com.milcesar.mscuentas.application.port.in.RegistrarMovimientoUseCase;
import com.milcesar.mscuentas.application.port.out.ListarMovimientosPort;
import jakarta.validation.Valid;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/movimientos")
@RequiredArgsConstructor
public class MovimientoController {
  private final RegistrarMovimientoUseCase registrarMovimiento;

  @PostMapping
  public ResponseEntity<MovimientoDTO> crear(
      @Valid @RequestBody RegistrarMovimientoCommand cmd,
      @RequestHeader(value = "Idempotency-Key", required = false) String idemKey) {
    var key = idemKey != null ? idemKey : UUID.randomUUID().toString();
    var dto = registrarMovimiento.registrar(cmd, key);
    return ResponseEntity.status(HttpStatus.CREATED).body(dto);
  }

  @GetMapping
  public List<MovimientoDTO> listar(
      @RequestParam String cuenta,
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant desde,
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant hasta,
      ListarMovimientosPort listarMovs) {
    return listarMovs.list(cuenta, desde, hasta).stream()
        .map(
            m ->
                MovimientoDTO.builder()
                    .fecha(m.getFecha())
                    .tipo(m.getTipo().name())
                    .valor(m.getValor())
                    .saldoPosterior(m.getSaldoPosterior())
                    .build())
        .toList();
  }
}
