package com.milcesar.mscuentas.infrastructure.adapter.out.persistence.jpa.entity;

import com.milcesar.mscuentas.domain.model.TipoCuenta;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "cuenta")
public class CuentaEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "numero_cuenta", unique = true, nullable = false)
  private String numeroCuenta;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private TipoCuenta tipo;

  @Column(name = "saldo_inicial", nullable = false)
  private BigDecimal saldo;

  @Column(nullable = false)
  private boolean estado;

  @Column(name = "cliente_id", nullable = false)
  private UUID clienteId;
}
