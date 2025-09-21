package com.milcesar.mscuentas.infrastructure.adapter.out.persistence.jpa.entity;

import com.milcesar.mscuentas.domain.model.TipoMovimiento;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "movimiento")
public class MovimientoEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private Instant fecha;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private TipoMovimiento tipo;

  @Column(nullable = false)
  private BigDecimal valor;

  @Column(name = "saldo_posterior", nullable = false)
  private BigDecimal saldoPosterior;

  @Column(name = "numero_cuenta", nullable = false)
  private String numeroCuenta;
}
