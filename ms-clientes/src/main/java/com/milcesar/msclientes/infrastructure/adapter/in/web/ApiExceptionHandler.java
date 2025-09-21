package com.milcesar.msclientes.infrastructure.adapter.in.web;

import com.milcesar.msclientes.domain.exception.BusinessRuleException;
import com.milcesar.msclientes.domain.exception.NotFoundException;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {
  @ExceptionHandler(BusinessRuleException.class)
  public ResponseEntity<Map<String, Object>> business(BusinessRuleException ex) {
    return ResponseEntity.badRequest()
        .body(Map.of("error", "BUSINESS_RULE", "message", ex.getMessage()));
  }

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<Map<String, Object>> notFound(NotFoundException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(Map.of("error", "NOT_FOUND", "message", ex.getMessage()));
  }
}
