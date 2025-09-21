package com.milcesar.mscuentas.domain.exception;

public class DomainException extends RuntimeException {
  protected DomainException(String message) {
    super(message);
  }
}
