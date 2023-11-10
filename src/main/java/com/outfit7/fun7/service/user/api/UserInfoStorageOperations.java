package com.outfit7.fun7.service.user.api;

import com.outfit7.fun7.service.user.api.dto.UserInfo;

public interface UserInfoStorageOperations {

  UserInfo getUserInfo(String userId);

  UserInfo saveUserInfo(UserInfo userInfoEntity);

  String deleteUserInfo(UserInfo userInfoEntity);
}
