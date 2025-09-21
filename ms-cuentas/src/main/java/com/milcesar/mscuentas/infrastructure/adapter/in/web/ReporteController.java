package com.milcesar.mscuentas.infrastructure.adapter.in.web;

import com.milcesar.mscuentas.application.command.GenerarReporteCommand;
import com.milcesar.mscuentas.application.dto.ReporteDTO;
import com.milcesar.mscuentas.application.port.in.GenerarReporteUseCase;
import java.time.Instant;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reportes")
@RequiredArgsConstructor
public class ReporteController {
  private final GenerarReporteUseCase useCase;

  @GetMapping
  public ReporteDTO generar(
      @RequestParam UUID cliente,
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant desde,
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant hasta) {
    return useCase.generar(
        GenerarReporteCommand.builder().clienteId(cliente).desde(desde).hasta(hasta).build());
  }
}
