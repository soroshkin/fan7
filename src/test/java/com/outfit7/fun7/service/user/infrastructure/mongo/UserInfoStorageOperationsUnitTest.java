package com.outfit7.fun7.service.user.infrastructure.mongo;

import com.outfit7.fun7.service.UnitTest;
import com.outfit7.fun7.service.user.api.dto.UserInfo;
import com.outfit7.fun7.service.user.api.dto.UserInfoNotFoundException;
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

class UserInfoStorageOperationsUnitTest extends UnitTest {

  @Mock
  private UserInfoRepository userInfoRepository;

  @Mock
  private UserInfoConverter userInfoConverter;

  @InjectMocks
  private UserInfoDatabaseService userInfoDatabaseService;

  @Test
  void shouldGetUserInfo() {
    // given
    String userId = "123";
    UserInfoEntity userInfoEntity = new UserInfoEntity(userId, "US", 3);
    UserInfo expectedUserInfo = new UserInfo(userId, "US", 3);

    when(userInfoRepository.findById(userId)).thenReturn(Optional.of(userInfoEntity));
    when(userInfoConverter.toUserInfo(userInfoEntity)).thenReturn(expectedUserInfo);

    // when
    UserInfo actualUserInfo = userInfoDatabaseService.getUserInfo(userId);

    // then
    assertThat(actualUserInfo).isEqualTo(expectedUserInfo);
    verify(userInfoRepository).findById(userId);
    verify(userInfoConverter).toUserInfo(userInfoEntity);
  }

  @Test
  void shouldGetUserInfoWhenUserNotFound() {
    // given
    String userId = "456";
    when(userInfoRepository.findById(userId)).thenReturn(Optional.empty());

    // when - then
    assertThatThrownBy(() -> userInfoDatabaseService.getUserInfo(userId))
      .isExactlyInstanceOf(UserInfoNotFoundException.class)
      .hasMessageContaining("user info is not found for userId " + userId);

    verify(userInfoRepository).findById(userId);
    verifyNoInteractions(userInfoConverter);
  }

  @Test
  void shouldSaveUserInfo() {
    // given
    UserInfo userInfo = new UserInfo("123", "US", 3);
    UserInfoEntity userInfoEntity = new UserInfoEntity("123", "US", 3);

    when(userInfoConverter.toEntity(userInfo)).thenReturn(userInfoEntity);
    when(userInfoRepository.save(userInfoEntity)).thenReturn(userInfoEntity);
    when(userInfoConverter.toUserInfo(userInfoEntity)).thenReturn(userInfo);

    // when
    UserInfo savedUserInfo = userInfoDatabaseService.saveUserInfo(userInfo);

    // then
    assertThat(savedUserInfo).isEqualTo(userInfo);
    verify(userInfoConverter).toEntity(userInfo);
    verify(userInfoRepository).save(userInfoEntity);
    verify(userInfoConverter).toUserInfo(userInfoEntity);
  }

  @Test
  void shouldDeleteUserInfo() {
    // given
    String userId = "123";
    UserInfo userInfo = new UserInfo(userId, "US", 3);
    UserInfoEntity userInfoEntityToDelete = new UserInfoEntity(userId, "US", 3);

    when(userInfoRepository.existsById(userId)).thenReturn(true);
    when(userInfoConverter.toEntity(userInfo)).thenReturn(userInfoEntityToDelete);

    // when
    String deletedUserId = userInfoDatabaseService.deleteUserInfo(userInfo);

    // then
    assertThat(deletedUserId).isEqualTo(userId);
    verify(userInfoRepository).existsById(userId);
    verify(userInfoConverter).toEntity(userInfo);
    verify(userInfoRepository).delete(userInfoEntityToDelete);
  }

  @Test
  void shouldNotDeleteUserInfoWhenUserNotFound() {
    // given
    String userId = "456";
    UserInfo userInfo = new UserInfo(userId, "US", 3);
    when(userInfoRepository.existsById(userId)).thenReturn(false);

    // when - then
    assertThatThrownBy(() -> userInfoDatabaseService.deleteUserInfo(userInfo))
      .isExactlyInstanceOf(UserInfoNotFoundException.class);

    verify(userInfoRepository).existsById(userId);
    verify(userInfoConverter).toEntity(userInfo);
    verifyNoMoreInteractions(userInfoConverter, userInfoRepository);
  }
}