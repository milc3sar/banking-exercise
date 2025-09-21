package com.milcesar.msclientes.infrastructure.adapter.out.persistence.jpa;

import com.milcesar.msclientes.infrastructure.adapter.out.persistence.jpa.entity.ClienteEntity;
import com.milcesar.msclientes.infrastructure.adapter.out.persistence.jpa.projection.ClienteListadoProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SpringDataClienteListadoRepository extends JpaRepository<ClienteEntity, Long> {

  @Query(
      value =
          """
                    select c.cliente_id as clienteId,
                           p.nombre    as nombre,
                           p.identificacion as identificacion,
                           c.estado    as estado
                      from cliente c
                      join persona p on p.id = c.persona_id
                     where (:pattern is null or p.identificacion ilike concat('%', :pattern, '%'))
                    """,
      nativeQuery = true)
  Page<ClienteListadoProjection> listarPorIdentificacion(
      @Param("pattern") String pattern, Pageable pageable);
}
