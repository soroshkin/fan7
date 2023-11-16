package com.outfit7.fun7.service.feature;

import com.outfit7.fun7.service.UnitTest;
import com.outfit7.fun7.service.feature.api.dto.FeatureState;
import com.outfit7.fun7.service.feature.api.dto.UserFeatures;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class ServiceCheckServiceUnitTest extends UnitTest {

  @Mock
  private MultiplayerService multiplayerService;

  @Mock
  private SupportFeatureService supportFeatureService;

  @Mock
  private AdsService adsService;

  @InjectMocks
  private ServiceCheckService serviceCheckService;

  @Test
  void shouldReturnUserFeatures() {
    // given
    String userId = "testUserId";
    String countryCode = "US";
    String timezone = "UTC";

    FeatureState givenMultiplayerFeatureState = FeatureState.ENABLED;
    FeatureState givenSupportFeatureState = FeatureState.DISABLED;
    FeatureState givenAdsFeatureState = FeatureState.ENABLED;

    when(multiplayerService.getMultiplayerFeatureState(userId, countryCode)).thenReturn(givenMultiplayerFeatureState);
    when(supportFeatureService.getSupportFeatureState()).thenReturn(givenSupportFeatureState);
    when(adsService.getAdsFeatureState(countryCode)).thenReturn(givenAdsFeatureState);

    // when
    UserFeatures userFeatures = serviceCheckService.getUserFeatures(timezone, userId, countryCode);

    // then
    assertThat(userFeatures.getMultiplayer()).isEqualTo(givenMultiplayerFeatureState);
    assertThat(userFeatures.getUserSupport()).isEqualTo(givenSupportFeatureState);
    assertThat(userFeatures.getAds()).isEqualTo(givenAdsFeatureState);
  }
}