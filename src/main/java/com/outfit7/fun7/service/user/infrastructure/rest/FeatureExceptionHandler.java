package com.outfit7.fun7.service.user.infrastructure.rest;

import com.outfit7.fun7.service.user.api.dto.AdsFeatureNotRetrievedException;
import com.outfit7.fun7.service.user.api.dto.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.Optional;

@RestControllerAdvice
public class FeatureExceptionHandler {

  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<String> handleUserInfoNotFoundException(UserNotFoundException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
  }

  @ExceptionHandler(AdsFeatureNotRetrievedException.class)
  public ResponseEntity<String> handleAdsFeatureNotRetrievedException(AdsFeatureNotRetrievedException ex) {
    return ResponseEntity.status(Optional.ofNullable(HttpStatus.resolve(ex.getStatusCode()))
        .orElse(HttpStatus.BAD_GATEWAY))
      .body(ex.getMessage());
  }
}
