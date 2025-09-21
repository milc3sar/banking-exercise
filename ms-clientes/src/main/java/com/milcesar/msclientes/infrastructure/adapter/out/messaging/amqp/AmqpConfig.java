package com.milcesar.msclientes.infrastructure.adapter.out.messaging.amqp;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmqpConfig {
  public static final String EXCHANGE_CLIENTES = "clientes.exchange";

  @Bean
  TopicExchange clientesExchange() {
    return new TopicExchange(EXCHANGE_CLIENTES, true, false);
  }
}
