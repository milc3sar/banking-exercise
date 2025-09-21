package com.milcesar.mscuentas.infrastructure.adapter.in.messaging.amqp;

import com.milcesar.mscuentas.application.port.out.PublicarMovimientoPort;
import com.milcesar.mscuentas.domain.event.MovimientoCreado;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AmqpMovimientoPublisher implements PublicarMovimientoPort {
  private final RabbitTemplate rabbit;

  @Override
  public void publish(MovimientoCreado evt) {
    rabbit.convertAndSend(AmqpConfig.EXCHANGE_CUENTAS, "movimiento.creado", evt);
  }
}
