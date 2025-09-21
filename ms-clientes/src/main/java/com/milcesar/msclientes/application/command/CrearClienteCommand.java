package com.milcesar.msclientes.application.command;

import com.milcesar.msclientes.application.dto.PersonaDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CrearClienteCommand {
  @Valid @NotNull private PersonaDTO persona;
  @NotBlank private String password;
}
