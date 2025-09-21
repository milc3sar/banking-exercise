package com.milcesar.msclientes.infrastructure.adapter.in.web;

import com.milcesar.msclientes.application.command.ActualizarEstadoClienteCommand;
import com.milcesar.msclientes.application.command.CrearClienteCommand;
import com.milcesar.msclientes.application.dto.ClienteDTO;
import com.milcesar.msclientes.application.dto.PersonaDTO;
import com.milcesar.msclientes.application.port.in.ActualizarEstadoClienteUseCase;
import com.milcesar.msclientes.application.port.in.CrearClienteUseCase;
import com.milcesar.msclientes.application.port.in.ListarClientesUseCase;
import com.milcesar.msclientes.application.port.in.ObtenerClienteUseCase;
import com.milcesar.msclientes.application.port.out.PersistirClientePort;
import com.milcesar.msclientes.application.port.out.PersistirPersonaPort;
import com.milcesar.msclientes.domain.exception.NotFoundException;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClienteController {
  private final CrearClienteUseCase crear;
  private final ActualizarEstadoClienteUseCase actualizarEstado;
  private final ObtenerClienteUseCase obtener;
  private final ListarClientesUseCase listar;

  @PostMapping
  public ResponseEntity<ClienteDTO> crear(@Valid @RequestBody CrearClienteCommand cmd) {
    return ResponseEntity.status(HttpStatus.CREATED).body(crear.crear(cmd));
  }

  @GetMapping("/{clienteId}")
  public ClienteDTO obtener(@PathVariable UUID clienteId) {
    return obtener.obtenerPorClienteId(clienteId);
  }

  @GetMapping
  public List<ClienteDTO> listar(
      @RequestParam(required = false) String q,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "20") int size) {
    return listar.listar(q, page, size);
  }

  // Activar/Inactivar
  @PatchMapping("/{clienteId}/estado")
  public ClienteDTO actualizarEstado(@PathVariable UUID clienteId, @RequestParam boolean estado) {
    return actualizarEstado.actualizarEstado(
        ActualizarEstadoClienteCommand.builder().clienteId(clienteId).estado(estado).build());
  }

  @PutMapping("/{clienteId}/persona")
  public ResponseEntity<Void> actualizarPersona(
      @PathVariable UUID clienteId,
      @Valid @RequestBody PersonaDTO persona,
      PersistirPersonaPort personaPort,
      PersistirClientePort clientePort) {
    var cliente =
        clientePort
            .findByClienteId(clienteId)
            .orElseThrow(() -> new NotFoundException("Cliente no existe"));
    var p =
        personaPort
            .findById(cliente.getPersonaId())
            .orElseThrow(() -> new NotFoundException("Persona no existe"));
    p.setNombre(persona.getNombre());
    p.setGenero(persona.getGenero());
    p.setEdad(persona.getEdad());
    p.setDireccion(persona.getDireccion());
    p.setTelefono(persona.getTelefono());
    personaPort.save(p);
    return ResponseEntity.noContent().build();
  }

  // “Delete” lógico opcional (inactivar)
  @DeleteMapping("/{clienteId}")
  public ResponseEntity<Void> eliminar(@PathVariable UUID clienteId) {
    actualizarEstado.actualizarEstado(
        ActualizarEstadoClienteCommand.builder().clienteId(clienteId).estado(false).build());
    return ResponseEntity.noContent().build();
  }
}
