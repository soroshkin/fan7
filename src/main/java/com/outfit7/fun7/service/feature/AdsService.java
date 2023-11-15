package com.outfit7.fun7.service.feature;

import com.outfit7.fun7.service.feature.api.AdsFeatureStateOperations;
import com.outfit7.fun7.service.feature.api.dto.AdsFeatureNotRetrievedException;
import com.outfit7.fun7.service.feature.api.dto.FeatureState;
import com.outfit7.fun7.service.feature.api.dto.FeignClientException;
import com.outfit7.fun7.service.feature.infrastructure.ads.AdsFeatureStateFeignClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
class AdsService implements AdsFeatureStateOperations {

  private final Logger logger = LoggerFactory.getLogger(getClass());

  private final AdsFeatureStateFeignClient adsFeatureStateFeignClient;

  AdsService(AdsFeatureStateFeignClient adsFeatureStateFeignClient) {
    this.adsFeatureStateFeignClient = adsFeatureStateFeignClient;
  }

  @Override
  public FeatureState getAdsFeatureState(String countryCode) {
    try {
      return adsFeatureStateFeignClient.getAdsFeatureState(countryCode).areAdsEnabled() ? FeatureState.ENABLED : FeatureState.DISABLED;
    } catch (FeignClientException e) {
      logger.warn(e.getMessage(), e);
      throw new AdsFeatureNotRetrievedException(e.getStatusCode(), "Could not retrieve ads feature state from external provider due to " + e.getMessage());
    }
  }
}
