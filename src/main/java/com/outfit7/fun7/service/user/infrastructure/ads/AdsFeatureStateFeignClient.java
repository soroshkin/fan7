package com.outfit7.fun7.service.user.infrastructure.ads;

import com.outfit7.fun7.service.user.api.dto.AdsFeatureStateResponse;
import feign.Param;
import feign.RequestLine;

public interface AdsFeatureStateFeignClient {

  @RequestLine("GET /fun7-ad-partner?countryCode={countryCode}")
  AdsFeatureStateResponse getAdsFeatureState(@Param("countryCode") String countryCode);
}
