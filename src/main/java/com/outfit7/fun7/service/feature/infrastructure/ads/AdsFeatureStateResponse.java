package com.outfit7.fun7.service.feature.infrastructure.ads;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

public class AdsFeatureStateResponse {

  @JsonDeserialize(using = JsonAdsFeatureStateDeserializer.class)
  private final boolean adsEnabled;

  @JsonCreator
  public AdsFeatureStateResponse(@JsonProperty("ads") boolean adsEnabled) {
    this.adsEnabled = adsEnabled;
  }

  public boolean areAdsEnabled() {
    return adsEnabled;
  }
}
