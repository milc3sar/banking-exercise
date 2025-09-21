package com.milcesar.msclientes.infrastructure.adapter.out.messaging.amqp;

import com.milcesar.msclientes.application.port.out.PublicarClientePort;
import com.milcesar.msclientes.domain.event.ClienteActualizado;
import com.milcesar.msclientes.domain.event.ClienteCreado;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AmqpClientePublisher implements PublicarClientePort {

  private final RabbitTemplate rabbit;

  @Override
  public void publishCreado(ClienteCreado evt) {
    log.info("Publicando evento 'cliente.creado' para clienteId: {}", evt.clienteId());

    rabbit.convertAndSend(AmqpConfig.EXCHANGE_CLIENTES, "cliente.creado", evt);
  }

  @Override
  public void publishActualizado(ClienteActualizado evt) {
    rabbit.convertAndSend(AmqpConfig.EXCHANGE_CLIENTES, "cliente.actualizado", evt);
  }
}
