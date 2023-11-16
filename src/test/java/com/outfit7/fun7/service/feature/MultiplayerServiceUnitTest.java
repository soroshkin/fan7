package com.outfit7.fun7.service.feature;

import com.outfit7.fun7.service.UnitTest;
import com.outfit7.fun7.service.feature.api.dto.FeatureState;
import com.outfit7.fun7.service.user.api.UserStorageOperations;
import com.outfit7.fun7.service.user.api.dto.User;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MultiplayerServiceUnitTest extends UnitTest {

  @Mock
  private UserStorageOperations userStorageOperations;

  @InjectMocks
  private MultiplayerService multiplayerService;

  @Test
  void shouldGetMultiplayerFeatureStateEnabled() {
    // given
    String userId = "123";
    String countryCode = "US";
    User user = new User(userId, 6);

    when(userStorageOperations.getUser(userId)).thenReturn(user);

    // when
    FeatureState result = multiplayerService.getMultiplayerFeatureState(userId, countryCode);

    // then
    assertThat(result).isEqualTo(FeatureState.ENABLED);
    verify(userStorageOperations).getUser(userId);
  }

  @Test
  void shouldGetMultiplayerFeatureStateDisabledWhenGameCountLessThanFive() {
    // given
    String userId = "456";
    String countryCode = "US";
    User user = new User(userId, 3);

    when(userStorageOperations.getUser(userId)).thenReturn(user);

    // when
    FeatureState result = multiplayerService.getMultiplayerFeatureState(userId, countryCode);

    // then
    assertThat(result).isEqualTo(FeatureState.DISABLED);
    verify(userStorageOperations).getUser(userId);
  }

  @Test
  void shouldGetMultiplayerFeatureStateDisabledFotNonUSUser() {
    // given
    String userId = "789";
    String countryCode = "CA";
    User user = new User(userId, 6);

    when(userStorageOperations.getUser(userId)).thenReturn(user);

    // when
    FeatureState result = multiplayerService.getMultiplayerFeatureState(userId, countryCode);

    // then
    assertThat(result).isEqualTo(FeatureState.DISABLED);
    verify(userStorageOperations).getUser(userId);
  }
}