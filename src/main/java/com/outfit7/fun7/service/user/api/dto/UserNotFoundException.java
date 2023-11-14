package com.outfit7.fun7.service.user.api.dto;

public class UserNotFoundException extends RuntimeException {

  public UserNotFoundException(String message) {
    super(message);
  }
}
