package com.outfit7.fun7.service.user;

import com.outfit7.fun7.service.user.api.ServiceCheckOperations;
import com.outfit7.fun7.service.user.api.dto.FeatureState;
import com.outfit7.fun7.service.user.api.dto.UserFeatures;
import org.springframework.stereotype.Service;

@Service
public class ServiceCheckService implements ServiceCheckOperations {

  private final MultiplayerService multiplayerService;

  private final SupportService supportService;

  private final AdsService adsService;

  public ServiceCheckService(MultiplayerService multiplayerService, SupportService supportService, AdsService adsService) {
    this.multiplayerService = multiplayerService;
    this.supportService = supportService;
    this.adsService = adsService;
  }

  @Override
  public UserFeatures getUserFeatures(String timezone, String userId, String countryCode) {
    FeatureState multiplayerFeatureState = multiplayerService.getMultiplayerFeatureState(userId, countryCode);
    FeatureState supportFeatureState = supportService.getSupportFeatureState();
    FeatureState adsFeatureState = adsService.getAdsFeatureState(countryCode);
    return UserFeatures.builder()
      .withMultiplayer(multiplayerFeatureState)
      .withUserSupport(supportFeatureState)
      .withAds(adsFeatureState)
      .build();
  }
}
