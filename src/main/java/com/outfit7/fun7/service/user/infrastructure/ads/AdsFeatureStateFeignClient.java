package com.outfit7.fun7.service.user.infrastructure.ads;

import com.outfit7.fun7.service.user.api.dto.FeatureState;
import feign.RequestLine;

public interface AdsFeatureStateFeignClient {

  @RequestLine("GET /fun7-ad-partner")
  FeatureState getAdsFeatureState(String countryCode);
}
