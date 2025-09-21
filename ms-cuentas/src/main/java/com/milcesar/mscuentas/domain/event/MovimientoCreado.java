package com.milcesar.mscuentas.domain.event;

import java.math.BigDecimal;
import java.time.Instant;

public record MovimientoCreado(
    String numeroCuenta, BigDecimal valor, BigDecimal saldoPosterior, Instant fecha) {}
