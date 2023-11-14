package com.outfit7.fun7.service.feature;

import com.outfit7.fun7.service.user.api.UserStorageOperations;
import com.outfit7.fun7.service.feature.api.dto.FeatureState;
import com.outfit7.fun7.service.user.api.dto.User;
import org.springframework.stereotype.Service;

@Service
class MultiplayerService {

  private static final String US_COUNTRY_CODE = "US";

  private static final int NUMBER_OF_GAMES_PLAYED_TO_ENABLE_MULTIPLAYER = 5;

  private final UserStorageOperations userStorageOperations;

  MultiplayerService(UserStorageOperations userStorageOperations) {
    this.userStorageOperations = userStorageOperations;
  }

  FeatureState getMultiplayerFeatureState(String userId, String countryCode) {
    User user = userStorageOperations.getUser(userId);
    if (countryCode.equals(US_COUNTRY_CODE) && user.getGameCount() > NUMBER_OF_GAMES_PLAYED_TO_ENABLE_MULTIPLAYER) {
      return FeatureState.ENABLED;
    }
    return FeatureState.DISABLED;
  }
}
