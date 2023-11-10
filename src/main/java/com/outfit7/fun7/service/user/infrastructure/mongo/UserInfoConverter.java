package com.outfit7.fun7.service.user.infrastructure.mongo;

import com.outfit7.fun7.service.user.api.dto.UserInfo;
import org.springframework.stereotype.Component;

@Component
public class UserInfoConverter {

  UserInfoEntity toEntity(UserInfo userInfo) {
    return new UserInfoEntity(userInfo.getUserId(), userInfo.getCountryCode(), userInfo.getGameCount());
  }

  UserInfo toUserInfo(UserInfoEntity userInfoEntity) {
    return new UserInfo(userInfoEntity.getUserId(), userInfoEntity.getCountryCode(), userInfoEntity.getGameCount());
  }
}
