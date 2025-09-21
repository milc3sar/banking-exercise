package com.milcesar.mscuentas.infrastructure.adapter.in.messaging.amqp;

import com.milcesar.mscuentas.application.dto.ClienteEventDTO;
import com.milcesar.mscuentas.infrastructure.adapter.out.persistence.jpa.entity.ClienteCacheEntity;
import com.milcesar.mscuentas.infrastructure.adapter.out.persistence.jpa.repository.SpringDataClienteCacheRepository;
import java.time.Instant;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClienteEventsListener {

  private final SpringDataClienteCacheRepository repo;

  @RabbitListener(queues = ClientesAmqpConfig.QUEUE_CLIENTES_CUENTAS)
  public void onMessage(@Payload ClienteEventDTO event) {
    var existing = repo.findById(event.getClienteId()).orElse(null);

    String nombre =
        event.getNombre() != null
            ? event.getNombre()
            : (existing != null ? existing.getNombre() : null);

    upsert(event.getClienteId(), nombre, event.isEstado());
  }

  private void upsert(UUID clienteId, String nombre, boolean estado) {
    var entity =
        ClienteCacheEntity.builder()
            .clienteId(clienteId)
            .nombre(nombre)
            .estado(estado)
            .updatedAt(Instant.now())
            .build();
    repo.save(entity);
  }
}
