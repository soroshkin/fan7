package com.outfit7.fun7.service.user.infrastructure.mongo;

import com.outfit7.fun7.service.UnitTest;
import com.outfit7.fun7.service.user.api.dto.User;
import com.outfit7.fun7.service.user.api.dto.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

class UserStorageOperationsUnitTest extends UnitTest {

  @Mock
  private UserRepository userRepository;

  @Mock
  private UserConverter userConverter;

  @InjectMocks
  private UserDatabaseService userDatabaseService;

  @Test
  void shouldGetUser() {
    // given
    String userId = "123";
    UserEntity userEntity = givenUserEntity(userId);
    User expectedUser = new User(userId, 3);

    when(userRepository.findByUserId(userId)).thenReturn(Optional.of(userEntity));
    when(userConverter.toUser(userEntity)).thenReturn(expectedUser);

    // when
    User actualUser = userDatabaseService.getUser(userId);

    // then
    assertThat(actualUser).isEqualTo(expectedUser);
    verify(userRepository).findByUserId(userId);
    verify(userConverter).toUser(userEntity);
  }

  @Test
  void shouldGetUserWhenUserNotFound() {
    // given
    String userId = "456";
    when(userRepository.findByUserId(userId)).thenReturn(Optional.empty());

    // when - then
    assertThatThrownBy(() -> userDatabaseService.getUser(userId))
      .isExactlyInstanceOf(UserNotFoundException.class)
      .hasMessageContaining("user is not found for userId " + userId);

    verify(userRepository).findByUserId(userId);
    verifyNoInteractions(userConverter);
  }

  @Test
  void shouldSaveUser() {
    // given
    User user = new User("123", 3);
    UserEntity userEntity = givenUserEntity(user.getUserId());

    when(userConverter.toEntity(user)).thenReturn(userEntity);
    when(userRepository.save(userEntity)).thenReturn(userEntity);
    when(userConverter.toUser(userEntity)).thenReturn(user);

    // when
    User savedUser = userDatabaseService.saveUser(user);

    // then
    assertThat(savedUser).isEqualTo(user);
    verify(userConverter).toEntity(user);
    verify(userRepository).save(userEntity);
    verify(userConverter).toUser(userEntity);
  }

  @Test
  void shouldDeleteUser() {
    // given
    String userId = "123";
    UserEntity userEntityToDelete = givenUserEntity(userId);

    when(userRepository.findByUserId(userId)).thenReturn(Optional.of(userEntityToDelete));

    // when
    String deletedUserId = userDatabaseService.deleteUserById(userId);

    // then
    assertThat(deletedUserId).isEqualTo(userId);
    verify(userRepository).findByUserId(userId);
    verifyNoInteractions(userConverter);
    verify(userRepository).delete(userEntityToDelete);
  }

  @Test
  void shouldNotDeleteUserWhenUserNotFound() {
    // given
    String userId = "456";
    when(userRepository.findByUserId(userId)).thenReturn(Optional.empty());

    // when - then
    assertThatThrownBy(() -> userDatabaseService.deleteUserById(userId))
      .isExactlyInstanceOf(UserNotFoundException.class);

    verify(userRepository).findByUserId(userId);
    verifyNoInteractions(userConverter);
    verifyNoMoreInteractions(userConverter, userRepository);
  }

  private UserEntity givenUserEntity(String userId) {
    return new UserEntity("655497c660897c5a1b622182", userId, 3);
  }
}