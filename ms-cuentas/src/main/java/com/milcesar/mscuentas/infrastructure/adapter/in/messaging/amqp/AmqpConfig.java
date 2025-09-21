package com.milcesar.mscuentas.infrastructure.adapter.in.messaging.amqp;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmqpConfig {
  public static final String EXCHANGE_CUENTAS = "cuentas.exchange";

  @Bean
  TopicExchange cuentasExchange() {
    return new TopicExchange(EXCHANGE_CUENTAS, true, false);
  }
}
