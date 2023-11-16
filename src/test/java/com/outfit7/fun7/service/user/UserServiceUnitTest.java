package com.outfit7.fun7.service.user;

import com.outfit7.fun7.service.UnitTest;
import com.outfit7.fun7.service.user.api.UserStorageOperations;
import com.outfit7.fun7.service.user.api.dto.User;
import com.outfit7.fun7.service.user.api.dto.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserServiceUnitTest extends UnitTest {

  @Mock
  private UserStorageOperations userStorageOperations;

  @InjectMocks
  private UserService userService;

  @Test
  void shouldReturnAllUsers() {
    // given
    List<User> expectedUsers = List.of(new User("1", "John", 5),
      new User("2", "Alice", 8));
    when(userStorageOperations.getAllUsers()).thenReturn(expectedUsers);

    // when
    List<User> actualUsers = userService.getAllUsers();

    // then
    assertThat(actualUsers).isEqualTo(expectedUsers);
    verify(userStorageOperations).getAllUsers();
  }

  @Test
  void shouldReturnUser() {
    // given
    String userId = "1";
    User expectedUser = new User(userId, "John", 5);
    when(userStorageOperations.getUser(userId)).thenReturn(expectedUser);

    // when
    User actualUser = userService.getUserById(userId);

    // then
    assertThat(actualUser).isEqualTo(expectedUser);
    verify(userStorageOperations).getUser(userId);
  }

  @Test
  void shouldThrowUserNotFoundException() {
    // given
    String invalidUserId = "999";
    when(userStorageOperations.getUser(invalidUserId)).thenThrow(new UserNotFoundException("User not found"));

    // when - then
    assertThatThrownBy(() -> userService.getUserById(invalidUserId))
      .isExactlyInstanceOf(UserNotFoundException.class);
    verify(userStorageOperations).getUser(invalidUserId);
  }

  @Test
  void shouldDeleteUserAndReturnUserId() {
    // given
    String userId = "1";
    when(userStorageOperations.deleteUserById(userId)).thenReturn(userId);

    // when
    String deletedUserId = userService.deleteUser(userId);

    // then
    assertThat(deletedUserId).isEqualTo(userId);
    verify(userStorageOperations).deleteUserById(userId);
  }
}