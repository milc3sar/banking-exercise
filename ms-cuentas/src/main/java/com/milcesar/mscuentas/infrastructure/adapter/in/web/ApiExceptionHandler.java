package com.milcesar.mscuentas.infrastructure.adapter.in.web;

import com.milcesar.mscuentas.domain.exception.BusinessRuleException;
import com.milcesar.mscuentas.domain.exception.NotFoundException;
import com.milcesar.mscuentas.infrastructure.exception.InfrastructureException;
import io.micrometer.core.instrument.config.validate.ValidationException;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {
  @ExceptionHandler(BusinessRuleException.class)
  ResponseEntity<Map<String, Object>> handleBusiness(BusinessRuleException ex) {
    return ResponseEntity.badRequest()
        .body(Map.of("error", "BUSINESS_RULE", "message", ex.getMessage()));
  }

  @ExceptionHandler(NotFoundException.class)
  ResponseEntity<Map<String, Object>> handleNotFound(NotFoundException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(Map.of("error", "NOT_FOUND", "message", ex.getMessage()));
  }

  @ExceptionHandler(ValidationException.class)
  ResponseEntity<Map<String, Object>> handleValidation(ValidationException ex) {
    return ResponseEntity.unprocessableEntity()
        .body(Map.of("error", "VALIDATION", "message", ex.getMessage()));
  }

  @ExceptionHandler(InfrastructureException.class)
  ResponseEntity<Map<String, Object>> handleInfra(InfrastructureException ex) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(Map.of("error", "INFRASTRUCTURE_ERROR", "message", ex.getMessage()));
  }
}
