package com.outfit7.fun7.service.feature.infrastructure.ads;

import feign.Param;
import feign.RequestLine;

public interface AdsFeatureStateFeignClient {

  @RequestLine("GET /fun7-ad-partner?countryCode={countryCode}")
  AdsFeatureStateResponse getAdsFeatureState(@Param("countryCode") String countryCode);
}
