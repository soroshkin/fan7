package com.outfit7.fun7.service.user.infrastructure.ads;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorMessage {

  private final String message;

  private final String errorCode;

  @JsonCreator
  public ErrorMessage(@JsonProperty("message") String message,
                      @JsonProperty("errorCode") String errorCode) {
    this.message = message;
    this.errorCode = errorCode;
  }

  public String getMessage() {
    return message;
  }

  public String getErrorCode() {
    return errorCode;
  }

  @Override
  public String toString() {
    return "ErrorMessage{" +
      "message='" + message + '\'' +
      ", errorCode='" + errorCode + '\'' +
      '}';
  }
}
