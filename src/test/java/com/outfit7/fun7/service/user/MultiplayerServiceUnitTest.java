package com.outfit7.fun7.service.user;

import com.outfit7.fun7.service.UnitTest;
import com.outfit7.fun7.service.user.api.UserInfoStorageOperations;
import com.outfit7.fun7.service.user.api.dto.FeatureState;
import com.outfit7.fun7.service.user.api.dto.UserInfo;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MultiplayerServiceUnitTest extends UnitTest {

  @Mock
  private UserInfoStorageOperations userInfoStorageOperations;

  @InjectMocks
  private MultiplayerService multiplayerService;

  @Test
  void shouldGetMultiplayerFeatureStateEnabled() {
    // given
    String userId = "123";
    String countryCode = "US";
    UserInfo userInfo = new UserInfo(userId, countryCode, 6);

    when(userInfoStorageOperations.getUserInfo(userId)).thenReturn(userInfo);

    // when
    FeatureState result = multiplayerService.getMultiplayerFeatureState(userId, countryCode);

    // then
    assertThat(result).isEqualTo(FeatureState.ENABLED);
    verify(userInfoStorageOperations).getUserInfo(userId);
  }

  @Test
  void shouldGetMultiplayerFeatureStateDisabledWhenGameCountLessThanFive() {
    // given
    String userId = "456";
    String countryCode = "US";
    UserInfo userInfo = new UserInfo(userId, countryCode, 3);

    when(userInfoStorageOperations.getUserInfo(userId)).thenReturn(userInfo);

    // when
    FeatureState result = multiplayerService.getMultiplayerFeatureState(userId, countryCode);

    // then
    assertThat(result).isEqualTo(FeatureState.DISABLED);
    verify(userInfoStorageOperations).getUserInfo(userId);
  }

  @Test
  void shouldGetMultiplayerFeatureStateDisabledFotNonUSUser() {
    // given
    String userId = "789";
    String countryCode = "CA";
    UserInfo userInfo = new UserInfo(userId, countryCode, 6);

    when(userInfoStorageOperations.getUserInfo(userId)).thenReturn(userInfo);

    // when
    FeatureState result = multiplayerService.getMultiplayerFeatureState(userId, countryCode);

    // then
    assertThat(result).isEqualTo(FeatureState.DISABLED);
    verify(userInfoStorageOperations).getUserInfo(userId);
  }
}