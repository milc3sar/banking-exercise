package com.milcesar.msclientes.application.dto;

import java.util.UUID;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClienteDTO {
  private UUID clienteId;
  private String nombre;
  private String identificacion;
  private boolean estado;
}
