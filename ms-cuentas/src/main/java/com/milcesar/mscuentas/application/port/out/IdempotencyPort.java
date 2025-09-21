package com.milcesar.mscuentas.application.port.out;

public interface IdempotencyPort {
  boolean exists(String key);

  void mark(String key);
}
