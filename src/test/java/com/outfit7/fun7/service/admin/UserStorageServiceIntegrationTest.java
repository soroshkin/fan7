package com.outfit7.fun7.service.admin;

import com.outfit7.fun7.service.NeedsTestsDataIntegrationTest;
import com.outfit7.fun7.service.admin.api.dto.UserNotFoundException;
import com.outfit7.fun7.service.admin.infrastructure.mongo.UserEntity;
import com.outfit7.fun7.service.admin.infrastructure.mongo.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UserStorageServiceIntegrationTest extends NeedsTestsDataIntegrationTest {

  @Autowired
  MongoTemplate mongoTemplate;
  @Autowired
  private UserStorageService userStorageService;

  @Autowired
  private UserRepository userRepository;

  @Test
  void shouldGetUser() {
    // given
    String userId = "123";
    UserEntity givenUserEntity = new UserEntity(userId, "Bob", "test@mail.com");
    populateDatabase("/mongo/correct_user.json");

    // when
    Optional<UserEntity> user = userStorageService.getUserById(userId);

    // then
    assertThat(user).isNotEmpty().get().usingRecursiveComparison().isEqualTo(givenUserEntity);
  }

  @Test
  void shouldThrowExceptionWhenUserNotFound() {
    // given
    String userId = "456";

    // when
    Optional<UserEntity> user = userStorageService.getUserById(userId);

    // then
    assertThat(user).isEmpty();
  }

  @Test
  void shouldDeleteUser() {
    // given
    String userId = "123";
    populateDatabase("/mongo/correct_user.json");

    // when
    String deletedUserId = userStorageService.deleteUserById(userId);

    // then
    assertThat(deletedUserId).isEqualTo(userId);
    assertThat(userRepository.existsById(userId)).isFalse();
  }

  @Test
  void shouldNotDeleteUserWhenUserNotFound() {
    // given
    String userId = "456";

    // when - then
    assertThatThrownBy(() -> userStorageService.deleteUserById(userId))
      .isExactlyInstanceOf(UserNotFoundException.class)
      .hasMessageContaining("user is not found with userId " + userId);
  }
}