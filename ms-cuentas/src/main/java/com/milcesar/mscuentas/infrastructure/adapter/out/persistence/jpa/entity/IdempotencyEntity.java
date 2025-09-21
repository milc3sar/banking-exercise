package com.milcesar.mscuentas.infrastructure.adapter.out.persistence.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "idempotency")
public class IdempotencyEntity {
  @Id
  @Column(name = "key", length = 80)
  private String key;

  @Column(name = "created_at", nullable = false)
  private Instant createdAt;
}
