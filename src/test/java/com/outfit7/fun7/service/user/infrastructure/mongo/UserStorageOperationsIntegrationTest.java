package com.outfit7.fun7.service.user.infrastructure.mongo;

import com.outfit7.fun7.service.NeedsTestsDataIntegrationTest;
import com.outfit7.fun7.service.user.api.dto.User;
import com.outfit7.fun7.service.user.api.dto.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UserStorageOperationsIntegrationTest extends NeedsTestsDataIntegrationTest {

  @Autowired
  private UserDatabaseService userDatabaseService;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private UserConverter userConverter;

  @Test
  void shouldGetUserInfo() {
    // given
    String userId = "123";
    populateDatabase("/mongo/correct_user.json");

    // when
    User actualUser = userDatabaseService.getUser(userId);

    // then
    assertThat(actualUser).isNotNull();
    assertThat(actualUser.getUserId()).isEqualTo(userId);
  }

  @Test
  void shouldNotGetUserInfoWhenUserNotFound() {
    // given
    String userId = "456";

    // when - then
    assertThatThrownBy(() -> userDatabaseService.getUser(userId))
      .isExactlyInstanceOf(UserNotFoundException.class)
      .hasMessageContaining("user is not found with userId " + userId);
  }

  @Test
  void shouldSaveUserInfo() {
    // given
    User user = new User("123", 3);

    // when
    User savedUser = userDatabaseService.saveUser(user);

    // then
    assertThat(savedUser).isNotNull();
    assertThat(savedUser.getUserId()).isEqualTo(user.getUserId());
    assertThat(savedUser.getGameCount()).isEqualTo(user.getGameCount());
  }

  @Test
  void shouldDeleteUserInfo() {
    // given
    String userId = "123";
    populateDatabase("/mongo/correct_user.json");

    // when
    String deletedUserId = userDatabaseService.deleteUserById(userId);

    // then
    assertThat(deletedUserId).isEqualTo(userId);
    assertThat(userRepository.existsById(userId)).isFalse();
  }

  @Test
  void shouldNotDeleteUserInfoWhenUserNotFound() {
    // given
    String userId = "456";

    // when - then
    assertThatThrownBy(() -> userDatabaseService.deleteUserById(userId))
      .isExactlyInstanceOf(UserNotFoundException.class)
      .hasMessageContaining("user is not found with userId " + userId);
  }
}
