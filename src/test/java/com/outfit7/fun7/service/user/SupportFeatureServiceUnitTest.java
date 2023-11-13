package com.outfit7.fun7.service.user;

import com.outfit7.fun7.service.user.api.dto.FeatureState;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class SupportFeatureServiceUnitTest {

  private static final ZoneId CET_ZONE_ID = ZoneId.of("Europe/Ljubljana");

  private Clock fixedClock;

  private SupportFeatureService supportFeatureService;

  @ParameterizedTest
  @MethodSource("provideTestData")
  void shouldReturnCorrectSupportFeatureState(String clockTime, FeatureState expectedState) {
    fixedClock = Clock.fixed(Instant.parse(clockTime), CET_ZONE_ID);
    supportFeatureService = new SupportFeatureService(fixedClock);

    // When
    FeatureState result = supportFeatureService.getSupportFeatureState();

    // Then
    assertThat(result).isEqualTo(expectedState);
  }

  private static Stream<Arguments> provideTestData() {
    return Stream.of(
      Arguments.of("2023-11-01T18:00:00Z", FeatureState.DISABLED),
      Arguments.of("2023-11-11T18:00:00Z", FeatureState.DISABLED),
      Arguments.of("2023-11-10T10:00:00Z", FeatureState.ENABLED),
      Arguments.of("2023-11-11T10:00:00Z", FeatureState.DISABLED),
      Arguments.of("2023-11-02T07:59:59Z", FeatureState.DISABLED),
      Arguments.of("2023-11-02T08:00:00Z", FeatureState.ENABLED),
      Arguments.of("2023-11-02T14:00:01Z", FeatureState.DISABLED),
      Arguments.of("2023-11-03T14:00:00Z", FeatureState.ENABLED),

      Arguments.of("2023-06-02T10:00:00Z", FeatureState.ENABLED),
      Arguments.of("2023-06-02T12:59:59Z", FeatureState.ENABLED),
      Arguments.of("2023-06-02T13:00:00Z", FeatureState.ENABLED),
      Arguments.of("2023-06-02T13:00:01Z", FeatureState.DISABLED),
      Arguments.of("2023-06-02T06:59:59Z", FeatureState.DISABLED),
      Arguments.of("2023-06-02T07:00:00Z", FeatureState.ENABLED),
      Arguments.of("2023-06-02T07:00:01Z", FeatureState.ENABLED),
      Arguments.of("2023-06-03T13:00:00Z", FeatureState.DISABLED),
      Arguments.of("2023-06-03T10:00:01Z", FeatureState.DISABLED),
      Arguments.of("2023-06-02T14:00:00Z", FeatureState.DISABLED)
    );
  }
}

