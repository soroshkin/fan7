package com.outfit7.fun7.service.user.infrastructure.rest;

import com.outfit7.fun7.service.IntegrationTest;
import com.outfit7.fun7.service.user.api.ServiceCheckOperations;
import com.outfit7.fun7.service.user.api.dto.FeatureState;
import com.outfit7.fun7.service.user.api.dto.UserFeatures;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@AutoConfigureMockMvc
class FeatureControllerIntegrationTest extends IntegrationTest {

  private static final String FEATURES_URL = "/features/state?timezone={timezone}&userId={userId}&cc={cc}";

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private TestRestTemplate testRestTemplate;

  @MockBean
  private ServiceCheckOperations serviceCheckOperations;

  @Test
  void getUserFeatureState() {
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
    ResponseEntity<UserFeatures> response = testRestTemplate.getForEntity(FEATURES_URL, UserFeatures.class, timezone, userId, cc);

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

  @ParameterizedTest
  @CsvSource({
    ",,,",
    "CET,,123",
    "CET,123,",
    ",123,US",
  })
  void shouldReturnBadRequestWhenParametersNotSet(String timezone, String userId, String cc) {
    // when
    ResponseEntity<String> response = testRestTemplate.getForEntity("/features/state?timezone={timezone}&userId={userId}&cc={cc}",
      String.class,
      timezone, userId, cc);

    // then
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
  }
}

