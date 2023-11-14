package com.outfit7.fun7.service.user.api.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.outfit7.fun7.service.user.infrastructure.ads.JsonAdsFeatureStateDeserializer;

public class AdsFeatureStateResponse {

  @JsonDeserialize(using = JsonAdsFeatureStateDeserializer.class)

  private final boolean adsEnabled;

  public boolean areAdsEnabled() {
    return adsEnabled;
  }

  @JsonCreator
  public AdsFeatureStateResponse(@JsonProperty("ads") boolean adsEnabled) {
    this.adsEnabled = adsEnabled;
  }
}
