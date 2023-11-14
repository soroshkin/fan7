package com.outfit7.fun7.service.user.infrastructure.ads;

import com.outfit7.fun7.service.IntegrationTest;
import com.outfit7.fun7.service.user.api.dto.AdsFeatureStateResponse;
import com.outfit7.fun7.service.user.api.dto.FeignClientException;
import org.glassfish.grizzly.http.Method;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.xebialabs.restito.builder.stub.StubHttp.whenHttp;
import static com.xebialabs.restito.builder.verify.VerifyHttp.verifyHttp;
import static com.xebialabs.restito.semantics.Action.ok;
import static com.xebialabs.restito.semantics.Action.resourceContent;
import static com.xebialabs.restito.semantics.Action.status;
import static com.xebialabs.restito.semantics.Condition.endsWithUri;
import static com.xebialabs.restito.semantics.Condition.method;
import static com.xebialabs.restito.semantics.Condition.parameter;
import static com.xebialabs.restito.semantics.Condition.withHeader;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AdsFeatureStateFeignClientIntegrationTest extends IntegrationTest {

  private static final String FUN_7_AD_PARTNER_URI = "/fun7-ad-partner";

  @Autowired
  private AdsFeatureStateFeignClient adsFeatureStateFeignClient;

  @Test
  void shouldGetAdsFeatureState() {
    // given
    String countryCode = "US";
    whenHttp(stubServer).match(endsWithUri(FUN_7_AD_PARTNER_URI), method(Method.GET))
      .then(ok(), resourceContent("mock/ads_partner_enabled_response.json"));

    // when
    AdsFeatureStateResponse featureState = adsFeatureStateFeignClient.getAdsFeatureState(countryCode);

    // then
    assertThat(featureState).isNotNull();
    assertThat(featureState.areAdsEnabled()).isTrue();
    verifyHttpRequest(countryCode);
  }

  @Test
  void shouldReturnAdsFeatureStateDisabled() {
    // given
    String countryCode = "US";
    whenHttp(stubServer).match(endsWithUri(FUN_7_AD_PARTNER_URI), method(Method.GET))
      .then(ok(), resourceContent("mock/ads_partner_disabled_response.json"));

    // when
    AdsFeatureStateResponse featureState = adsFeatureStateFeignClient.getAdsFeatureState(countryCode);

    // then
    assertThat(featureState).isNotNull();
    assertThat(featureState.areAdsEnabled()).isFalse();
    verifyHttpRequest(countryCode);
  }

  @Test
  void shouldReturnBadRequest() {
    // given
    String countryCode = "US";
    whenHttp(stubServer).match(endsWithUri(FUN_7_AD_PARTNER_URI), method(Method.GET))
      .then(status(HttpStatus.BAD_REQUEST_400));

    // when - then
    assertThatThrownBy(() -> adsFeatureStateFeignClient.getAdsFeatureState(countryCode))
      .isExactlyInstanceOf(FeignClientException.class);
    verifyHttpRequest(countryCode);
  }

  private void verifyHttpRequest(String countryCode) {
    verifyHttp(stubServer).once(endsWithUri(FUN_7_AD_PARTNER_URI),
      method(Method.GET),
      parameter("countryCode", countryCode),
      withHeader("Authorization", "Basic ZnVuN3VzZXI6ZnVuN3Bhc3M="));
  }
}
