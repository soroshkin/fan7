package com.outfit7.fun7.service.user.infrastructure.rest;

import com.outfit7.fun7.service.RestIntegrationTest;
import com.outfit7.fun7.service.user.api.ServiceCheckOperations;
import com.outfit7.fun7.service.user.api.dto.AdsFeatureNotRetrievedException;
import com.outfit7.fun7.service.user.api.dto.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.validation.ConstraintViolationException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class FeatureExceptionHandlerIntegrationTest extends RestIntegrationTest {

  private static final String FEATURES_URL = "/features/state?timezone=CET&userId=123&cc=US";

  @MockBean
  private ServiceCheckOperations serviceCheckOperations;

  @Test
  void shouldHandleUserInfoNotFoundException() {
    // given
    when(serviceCheckOperations.getUserFeatures(any(), any(), any()))
      .thenThrow(new UserNotFoundException("User not found"));

    // when
    ResponseEntity<String> response = testRestTemplate.getForEntity(FEATURES_URL, String.class);

    // then
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    assertThat(response.getBody()).isEqualTo("User not found");
    verify(serviceCheckOperations).getUserFeatures(any(), any(), any());
  }

  @Test
  void shouldHandleConstraintViolationException() {
    // given
    when(serviceCheckOperations.getUserFeatures(any(), any(), any()))
      .thenThrow(new ConstraintViolationException("Validation failed", null));

    // when
    ResponseEntity<String> response = testRestTemplate.getForEntity(FEATURES_URL, String.class);

    // then
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(response.getBody()).isEqualTo("Validation failed");
    verify(serviceCheckOperations).getUserFeatures(any(), any(), any());
  }

  @Test
  void shouldHandleAdsFeatureNotRetrievedException() {
    // given
    AdsFeatureNotRetrievedException exception = new AdsFeatureNotRetrievedException(HttpStatus.GATEWAY_TIMEOUT.value(), "Ads feature not retrieved");
    when(serviceCheckOperations.getUserFeatures(any(), any(), any())).thenThrow(exception);

    // when
    ResponseEntity<String> response = testRestTemplate.getForEntity(FEATURES_URL, String.class);

    // then
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.GATEWAY_TIMEOUT);
    assertThat(response.getBody()).isEqualTo("Ads feature not retrieved");
    verify(serviceCheckOperations).getUserFeatures(any(), any(), any());
  }
}