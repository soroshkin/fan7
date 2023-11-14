package com.outfit7.fun7.service.user.api.dto;

public class FeignClientException extends RuntimeException {

  private final Integer statusCode;

  private final String errorCode;

  private final String errorMessage;

  public FeignClientException(Integer statusCode, String errorCode, String errorMessage) {
    this.statusCode = statusCode;
    this.errorCode = errorCode;
    this.errorMessage = errorMessage;
  }

  public Integer getStatusCode() {
    return statusCode;
  }

  public String getErrorCode() {
    return errorCode;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  @Override
  public String toString() {
    return "FeignClientException{" +
      "statusCode=" + statusCode +
      ", errorCode='" + errorCode + '\'' +
      ", errorMessage='" + errorMessage + '\'' +
      '}';
  }
}
