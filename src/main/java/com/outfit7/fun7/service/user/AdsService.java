package com.outfit7.fun7.service.user;

import com.outfit7.fun7.service.user.api.AdsFeatureStateOperations;
import com.outfit7.fun7.service.user.api.dto.FeatureState;
import com.outfit7.fun7.service.user.infrastructure.ads.AdsFeatureStateFeignClient;
import org.springframework.stereotype.Service;

@Service
class AdsService implements AdsFeatureStateOperations{

  private final AdsFeatureStateFeignClient adsFeatureStateFeignClient;

  AdsService(AdsFeatureStateFeignClient adsFeatureStateFeignClient) {
    this.adsFeatureStateFeignClient = adsFeatureStateFeignClient;
  }

  @Override
  public FeatureState getAdsFeatureState(String countryCode) {
    return adsFeatureStateFeignClient.getAdsFeatureState(countryCode);
  }
}
