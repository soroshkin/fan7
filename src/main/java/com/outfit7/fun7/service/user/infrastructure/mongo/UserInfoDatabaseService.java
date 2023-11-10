package com.outfit7.fun7.service.user.infrastructure.mongo;

import com.outfit7.fun7.service.user.api.UserInfoStorageOperations;
import com.outfit7.fun7.service.user.api.dto.UserInfo;
import com.outfit7.fun7.service.user.api.dto.UserInfoNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserInfoDatabaseService implements UserInfoStorageOperations {

  private static final String USER_INFO_NOT_FOUND_ERROR_MESSAGE = "user info is not found for userId %s";

  private final UserInfoRepository userInfoRepository;

  private final UserInfoConverter userInfoConverter;

  public UserInfoDatabaseService(UserInfoRepository userInfoRepository, UserInfoConverter userInfoConverter) {
    this.userInfoRepository = userInfoRepository;
    this.userInfoConverter = userInfoConverter;
  }

  @Override
  public UserInfo getUserInfo(String userId) {
    return userInfoRepository.findById(userId)
      .map(userInfoConverter::toUserInfo)
      .orElseThrow(() -> new UserInfoNotFoundException(String.format(USER_INFO_NOT_FOUND_ERROR_MESSAGE, userId)));
  }

  @Override
  public UserInfo saveUserInfo(UserInfo userInfo) {
    UserInfoEntity userInfoEntity = userInfoConverter.toEntity(userInfo);
    UserInfoEntity savedUserInfoEntity = userInfoRepository.save(userInfoEntity);
    return userInfoConverter.toUserInfo(savedUserInfoEntity);
  }

  @Override
  public String deleteUserInfo(UserInfo userInfo) {
    String userId = userInfo.getUserId();
    UserInfoEntity userInfoEntityToDelete = userInfoConverter.toEntity(userInfo);

    if (!userInfoRepository.existsById(userId)) {
      throw new UserInfoNotFoundException(String.format(USER_INFO_NOT_FOUND_ERROR_MESSAGE, userId));
    }
    userInfoRepository.delete(userInfoEntityToDelete);

    return userId;
  }
}
