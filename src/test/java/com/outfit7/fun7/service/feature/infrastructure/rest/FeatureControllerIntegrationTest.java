package com.outfit7.fun7.service.feature.infrastructure.rest;

import com.outfit7.fun7.service.RestIntegrationTest;
import com.outfit7.fun7.service.feature.api.ServiceCheckOperations;
import com.outfit7.fun7.service.feature.api.dto.FeatureState;
import com.outfit7.fun7.service.feature.api.dto.UserFeatures;
import com.outfit7.fun7.service.user.api.dto.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class FeatureControllerIntegrationTest extends RestIntegrationTest {

  private static final String FEATURES_URL = "/features/state?timezone={timezone}&userId={userId}&cc={cc}";

  @MockBean
  private ServiceCheckOperations serviceCheckOperations;

  @Test
  void shouldGetUserFeatureState() {
    // given
    UserFeatures givenUserFeatures = UserFeatures.builder()
      .withMultiplayer(FeatureState.ENABLED)
      .withUserSupport(FeatureState.ENABLED)
      .withAds(FeatureState.ENABLED)
      .build();
    String timezone = "CET";
    String cc = "US";
    String userId = "123";
    when(serviceCheckOperations.getUserFeatures(anyString(), anyString(), anyString())).thenReturn(givenUserFeatures);

    // when
    ResponseEntity<UserFeatures> response = testRestTemplate
      .withBasicAuth(USERNAME, PASSWORD)
      .getForEntity(FEATURES_URL, UserFeatures.class, timezone, userId, cc);

    // then
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isNotNull();
    UserFeatures userFeatures = response.getBody();
    assertThat(userFeatures).isNotNull();
    assertThat(userFeatures.getAds()).isEqualTo(FeatureState.ENABLED);
    assertThat(userFeatures.getUserSupport()).isEqualTo(FeatureState.ENABLED);
    assertThat(userFeatures.getMultiplayer()).isEqualTo(FeatureState.ENABLED);
    verify(serviceCheckOperations).getUserFeatures(timezone, userId, cc);
  }

  @Test
  void shouldGetUserFeatureStateWithAdminCredentials() {
    // given
    UserFeatures givenUserFeatures = UserFeatures.builder()
      .withMultiplayer(FeatureState.ENABLED)
      .withUserSupport(FeatureState.ENABLED)
      .withAds(FeatureState.ENABLED)
      .build();
    String timezone = "CET";
    String cc = "US";
    String userId = "123";
    when(serviceCheckOperations.getUserFeatures(anyString(), anyString(), anyString())).thenReturn(givenUserFeatures);

    // when
    ResponseEntity<UserFeatures> response = testRestTemplate
      .withBasicAuth("admin", "admin")
      .getForEntity(FEATURES_URL, UserFeatures.class, timezone, userId, cc);

    // then
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isNotNull();
    UserFeatures userFeatures = response.getBody();
    assertThat(userFeatures).isNotNull();
    assertThat(userFeatures.getAds()).isEqualTo(FeatureState.ENABLED);
    assertThat(userFeatures.getUserSupport()).isEqualTo(FeatureState.ENABLED);
    assertThat(userFeatures.getMultiplayer()).isEqualTo(FeatureState.ENABLED);
    verify(serviceCheckOperations).getUserFeatures(timezone, userId, cc);
  }

  @Test
  void shouldReturn404WhenUserInfoNotFoundExceptionIsThrown() {
    // given
    String timezone = "CET";
    String cc = "US";
    String userId = "123";
    String errorMessage = "user info not found";
    when(serviceCheckOperations.getUserFeatures(anyString(), anyString(), anyString())).thenThrow(new UserNotFoundException(errorMessage));

    // when
    ResponseEntity<String> response = testRestTemplate
      .withBasicAuth(USERNAME, PASSWORD)
      .getForEntity(FEATURES_URL, String.class, timezone, userId, cc);

    // then
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    assertThat(response.getBody()).isNotNull().isEqualTo(errorMessage);
    verify(serviceCheckOperations).getUserFeatures(timezone, userId, cc);
  }

  @ParameterizedTest
  @CsvSource({
    ",,,",
    "CET,,123",
    "CET,123,",
    ",123,US",
  })
  void shouldReturnBadRequestWhenParametersAreAbsent(String timezone, String userId, String cc) {
    // when
    ResponseEntity<String> response = testRestTemplate
      .withBasicAuth(USERNAME, PASSWORD)
      .getForEntity("/features/state?timezone={timezone}&userId={userId}&cc={cc}",
        String.class,
        timezone, userId, cc);

    // then
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
  }
}

