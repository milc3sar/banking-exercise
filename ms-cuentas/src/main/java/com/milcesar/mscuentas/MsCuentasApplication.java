package com.milcesar.mscuentas;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableRabbit
public class MsCuentasApplication {

  public static void main(String[] args) {
    SpringApplication.run(MsCuentasApplication.class, args);
  }
}
