package com.outfit7.fun7.service.feature.infrastructure.ads;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

class ErrorResponse {

  private final Long timestamp;

  private final List<ErrorMessage> errorMessages;

  @JsonCreator
  ErrorResponse(@JsonProperty("timestamp") Long timestamp,
                       @JsonProperty("errorMessages") List<ErrorMessage> errorMessages) {
    this.timestamp = timestamp;
    this.errorMessages = errorMessages;
  }

  public Long getTimestamp() {
    return timestamp;
  }

  public List<ErrorMessage> getErrorMessages() {
    return errorMessages;
  }

  @Override
  public String toString() {
    return "ErrorResponse{" +
      "timestamp=" + timestamp +
      ", errorMessages=" + errorMessages +
      '}';
  }
}
