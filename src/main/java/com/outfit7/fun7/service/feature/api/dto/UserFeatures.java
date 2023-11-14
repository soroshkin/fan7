package com.outfit7.fun7.service.feature.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(builder = UserFeatures.Builder.class)
public class UserFeatures {

  private final FeatureState multiplayer;

  @JsonProperty("user-support")
  private final FeatureState userSupport;

  private final FeatureState ads;

  private UserFeatures(Builder builder) {
    this.multiplayer = builder.getMultiplayer();
    this.userSupport = builder.getUserSupport();
    this.ads = builder.getAds();
  }

  public static Builder builder() {
    return new Builder();
  }

  public FeatureState getMultiplayer() {
    return multiplayer;
  }

  public FeatureState getUserSupport() {
    return userSupport;
  }

  public FeatureState getAds() {
    return ads;
  }

  @Override
  public String toString() {
    return "FeaturesInfo{" +
      "multiplayer=" + multiplayer +
      ", userSupport=" + userSupport +
      ", ads=" + ads +
      '}';
  }

  public static class Builder {

    private FeatureState multiplayer;

    private FeatureState userSupport;

    private FeatureState ads;

    private Builder() {
    }

    public Builder withMultiplayer(FeatureState multiplayer) {
      this.multiplayer = multiplayer;
      return this;
    }

    @JsonProperty("user-support")
    public Builder withUserSupport(FeatureState userSupport) {
      this.userSupport = userSupport;
      return this;
    }

    public Builder withAds(FeatureState ads) {
      this.ads = ads;
      return this;
    }

    public FeatureState getMultiplayer() {
      return multiplayer;
    }

    public FeatureState getUserSupport() {
      return userSupport;
    }

    public FeatureState getAds() {
      return ads;
    }

    public UserFeatures build() {
      return new UserFeatures(this);
    }
  }
}
