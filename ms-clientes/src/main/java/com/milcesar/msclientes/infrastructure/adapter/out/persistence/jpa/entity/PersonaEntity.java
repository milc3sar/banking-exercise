package com.milcesar.msclientes.infrastructure.adapter.out.persistence.jpa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "persona")
@Getter
@Setter
public class PersonaEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String nombre;

  private String genero;
  private Integer edad;

  @Column(nullable = false, unique = true)
  private String identificacion;

  private String direccion;
  private String telefono;
}
