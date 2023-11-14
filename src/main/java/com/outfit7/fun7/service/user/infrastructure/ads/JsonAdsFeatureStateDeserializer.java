package com.outfit7.fun7.service.user.infrastructure.ads;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class JsonAdsFeatureStateDeserializer extends JsonDeserializer<Boolean> {

  private static final String FEATURE_ENABLED = "sure, why not!";

  private static final String FEATURE_DISABLED = "you shall not pass!";

  @Override
  public Boolean deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
    String adsValue = p.getValueAsString();
    return switch (adsValue) {
      case FEATURE_ENABLED -> true;
      case FEATURE_DISABLED -> false;
      default -> throw new JsonParseException(p, String.format("Could not parse response: %s", adsValue));
    };
  }
}
