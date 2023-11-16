package com.outfit7.fun7.service.feature;

import com.outfit7.fun7.service.UnitTest;
import com.outfit7.fun7.service.feature.api.dto.AdsFeatureNotRetrievedException;
import com.outfit7.fun7.service.feature.api.dto.FeatureState;
import com.outfit7.fun7.service.feature.api.dto.FeignClientException;
import com.outfit7.fun7.service.feature.infrastructure.ads.AdsFeatureStateFeignClient;
import com.outfit7.fun7.service.feature.infrastructure.ads.AdsFeatureStateResponse;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AdsServiceUnitTest extends UnitTest {

  @Mock
  private AdsFeatureStateFeignClient adsFeatureStateFeignClient;

  @InjectMocks
  private AdsService adsService;

  @Test
  void shouldReturnEnabledWhenAdsAreEnabled() {
    // given
    String countryCode = "US";
    when(adsFeatureStateFeignClient.getAdsFeatureState(countryCode)).thenReturn(new AdsFeatureStateResponse(true));

    // when
    FeatureState featureState = adsService.getAdsFeatureState(countryCode);

    // then
    assertThat(featureState).isEqualTo(FeatureState.ENABLED);
    verify(adsFeatureStateFeignClient).getAdsFeatureState(countryCode);
  }

  @Test
  void shouldReturnDisabledWhenAdsAreDisabled() {
    // given
    String countryCode = "CA";
    when(adsFeatureStateFeignClient.getAdsFeatureState(countryCode)).thenReturn(new AdsFeatureStateResponse(false));

    // when
    FeatureState featureState = adsService.getAdsFeatureState(countryCode);

    // then
    assertThat(featureState).isEqualTo(FeatureState.DISABLED);
    verify(adsFeatureStateFeignClient).getAdsFeatureState(countryCode);
  }

  @Test
  void shouldThrowExceptionOnFeignClientException() {
    // given
    String countryCode = "US";
    String errorMessage = "Internal Server Error";
    when(adsFeatureStateFeignClient.getAdsFeatureState(countryCode)).thenThrow(new FeignClientException(500, errorMessage));

    // when - then
    assertThatThrownBy(() -> adsService.getAdsFeatureState(countryCode))
      .isExactlyInstanceOf(AdsFeatureNotRetrievedException.class)
      .hasMessageContaining("Could not retrieve ads feature state from external provider due to " + errorMessage);
    verify(adsFeatureStateFeignClient).getAdsFeatureState(countryCode);
  }
}
