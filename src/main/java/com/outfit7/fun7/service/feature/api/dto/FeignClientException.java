package com.outfit7.fun7.service.feature.api.dto;

public class FeignClientException extends RuntimeException {

  private final Integer statusCode;

  private final String errorMessage;

  public FeignClientException(Integer statusCode, String errorMessage) {
    this.statusCode = statusCode;
    this.errorMessage = errorMessage;
  }

  public Integer getStatusCode() {
    return statusCode;
  }

  @Override
  public String getMessage() {
    return errorMessage;
  }

  @Override
  public String toString() {
    return "FeignClientException{" +
      "statusCode=" + statusCode +
      ", errorMessage='" + errorMessage + '\'' +
      '}';
  }
}
