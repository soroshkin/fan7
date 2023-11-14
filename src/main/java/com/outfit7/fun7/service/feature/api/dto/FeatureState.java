package com.outfit7.fun7.service.feature.api.dto;

import com.fasterxml.jackson.annotation.JsonValue;

public enum FeatureState {
  ENABLED("enabled"),
  DISABLED("disabled");

  private final String value;

  FeatureState(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  @JsonValue
  public String toValue() {
    return this.getValue();
  }
}
