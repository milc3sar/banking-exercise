package com.milcesar.msclientes.application.port.out;

import com.milcesar.msclientes.domain.model.Persona;
import java.util.Optional;

public interface PersistirPersonaPort {
  Persona save(Persona p);

  Optional<Persona> findByIdentificacion(String identificacion);

  Optional<Persona> findById(Long id);
}
