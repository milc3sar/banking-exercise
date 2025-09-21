package com.milcesar.mscuentas.infrastructure.adapter.out.persistence.jpa.entity;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "cliente_cache")
public class ClienteCacheEntity {
  @Id
  @Column(name = "cliente_id")
  private UUID clienteId;

  private String nombre;
  private boolean estado;

  @Column(name = "updated_at")
  private Instant updatedAt;
}
