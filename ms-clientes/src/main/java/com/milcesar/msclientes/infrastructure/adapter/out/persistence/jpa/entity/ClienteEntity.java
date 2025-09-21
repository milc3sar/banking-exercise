package com.milcesar.msclientes.infrastructure.adapter.out.persistence.jpa.entity;

import jakarta.persistence.*;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "cliente")
@Getter
@Setter
public class ClienteEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "cliente_id", nullable = false, unique = true)
  private UUID clienteId;

  @Column(name = "persona_id", nullable = false)
  private Long personaId;

  @Column(name = "password", nullable = false)
  private String passwordHash;

  @Column(nullable = false)
  private boolean estado;
}
