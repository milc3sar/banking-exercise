package com.milcesar.msclientes.application.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonaDTO {
  @NotBlank private String nombre;
  private String genero;

  @Min(0)
  private Integer edad;

  @NotBlank private String identificacion;
  private String direccion;
  private String telefono;
}
