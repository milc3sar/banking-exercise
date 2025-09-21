package com.milcesar.msclientes.domain.model;

import com.milcesar.msclientes.domain.exception.BusinessRuleException;
import java.util.UUID;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cliente {
  private Long id;
  private UUID clienteId;
  private Long personaId;
  private String passwordHash;
  private boolean estado;

  public void activar() {
    this.estado = true;
  }

  public void inactivar() {
    this.estado = false;
  }

  public void setPasswordHash(String hash) {
    if (hash == null || hash.isBlank()) throw new BusinessRuleException("Password inválido");
    this.passwordHash = hash;
  }
}
