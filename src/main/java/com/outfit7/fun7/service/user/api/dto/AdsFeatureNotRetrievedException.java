package com.outfit7.fun7.service.user.api.dto;

public class AdsFeatureNotRetrievedException extends RuntimeException {

  private final int statusCode;

  public AdsFeatureNotRetrievedException(int statusCode, String message) {
    super(message);
    this.statusCode = statusCode;
  }

  public int getStatusCode() {
    return statusCode;
  }
}
