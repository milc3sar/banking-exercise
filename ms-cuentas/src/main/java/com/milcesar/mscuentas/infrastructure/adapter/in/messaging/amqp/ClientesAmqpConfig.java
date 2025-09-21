package com.milcesar.mscuentas.infrastructure.adapter.in.messaging.amqp;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientesAmqpConfig {
  public static final String EXCHANGE_CLIENTES = "clientes.exchange";
  public static final String QUEUE_CLIENTES_CUENTAS = "clientes.cuentas.q";

  @Bean
  TopicExchange clientesExchange() {
    return new TopicExchange(EXCHANGE_CLIENTES, true, false);
  }

  @Bean
  Queue clientesCuentasQueue() {
    return QueueBuilder.durable(QUEUE_CLIENTES_CUENTAS).build();
  }

  @Bean
  Binding bindClienteCreado(Queue clientesCuentasQueue, TopicExchange clientesExchange) {
    return BindingBuilder.bind(clientesCuentasQueue).to(clientesExchange).with("cliente.creado");
  }

  @Bean
  Binding bindClienteActualizado(Queue clientesCuentasQueue, TopicExchange clientesExchange) {
    return BindingBuilder.bind(clientesCuentasQueue)
        .to(clientesExchange)
        .with("cliente.actualizado");
  }
}
