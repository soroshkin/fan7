package com.outfit7.fun7.service.user;

import com.outfit7.fun7.service.user.api.UserInfoStorageOperations;
import com.outfit7.fun7.service.user.api.dto.FeatureState;
import com.outfit7.fun7.service.user.api.dto.UserInfo;
import org.springframework.stereotype.Service;

@Service
class MultiplayerService {

  private static final String US_COUNTRY_CODE = "US";

  private static final int NUMBER_OF_GAMES_PLAYED_TO_ENABLE_MULTIPLAYER = 5;

  private final UserInfoStorageOperations userInfoStorageOperations;

  MultiplayerService(UserInfoStorageOperations userInfoStorageOperations) {
    this.userInfoStorageOperations = userInfoStorageOperations;
  }

  FeatureState getMultiplayerFeatureState(String userId, String countryCode) {
    UserInfo userInfo = userInfoStorageOperations.getUserInfo(userId);
    if (countryCode.equals(US_COUNTRY_CODE) && userInfo.getGameCount() > NUMBER_OF_GAMES_PLAYED_TO_ENABLE_MULTIPLAYER) {
      return FeatureState.ENABLED;
    }
    return FeatureState.DISABLED;
  }
}
