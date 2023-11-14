package com.outfit7.fun7.service.user.infrastructure.mongo;

import com.outfit7.fun7.service.NeedsTestsDataIntegrationTest;
import com.outfit7.fun7.service.user.api.dto.UserInfo;
import com.outfit7.fun7.service.user.api.dto.UserInfoNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UserInfoStorageOperationsIntegrationTest extends NeedsTestsDataIntegrationTest {

  @Autowired
  private UserInfoDatabaseService userInfoDatabaseService;

  @Autowired
  private UserInfoRepository userInfoRepository;

  @Autowired
  private UserInfoConverter userInfoConverter;

  @Test
  void shouldGetUserInfo() {
    // given
    String userId = "123";
    populateDatabase("/mongo/correct_user_info.json");

    // when
    UserInfo actualUserInfo = userInfoDatabaseService.getUserInfo(userId);

    // then
    assertThat(actualUserInfo).isNotNull();
    assertThat(actualUserInfo.getUserId()).isEqualTo(userId);
  }

  @Test
  void shouldNotGetUserInfoWhenUserNotFound() {
    // given
    String userId = "456";

    // when - then
    assertThatThrownBy(() -> userInfoDatabaseService.getUserInfo(userId))
      .isExactlyInstanceOf(UserInfoNotFoundException.class)
      .hasMessageContaining("user info is not found for userId " + userId);
  }

  @Test
  void shouldSaveUserInfo() {
    // given
    UserInfo userInfo = new UserInfo("123", "US", 3);

    // when
    UserInfo savedUserInfo = userInfoDatabaseService.saveUserInfo(userInfo);

    // then
    assertThat(savedUserInfo).isNotNull();
    assertThat(savedUserInfo.getUserId()).isEqualTo(userInfo.getUserId());
    assertThat(savedUserInfo.getCountryCode()).isEqualTo(userInfo.getCountryCode());
    assertThat(savedUserInfo.getGameCount()).isEqualTo(userInfo.getGameCount());
  }

  @Test
  void shouldDeleteUserInfo() {
    // given
    String userId = "123";
    populateDatabase("/mongo/correct_user_info.json");

    // when
    String deletedUserId = userInfoDatabaseService.deleteUserInfo(new UserInfo(userId, "US", 3));

    // then
    assertThat(deletedUserId).isEqualTo(userId);
    assertThat(userInfoRepository.existsById(userId)).isFalse();
  }

  @Test
  void shouldNotDeleteUserInfoWhenUserNotFound() {
    // given
    String userId = "456";

    // when - then
    assertThatThrownBy(() -> userInfoDatabaseService.deleteUserInfo(new UserInfo(userId, "US", 3)))
      .isExactlyInstanceOf(UserInfoNotFoundException.class)
      .hasMessageContaining("user info is not found for userId " + userId);
  }
}
