package com.outfit7.fun7.service.user;

import com.outfit7.fun7.service.user.api.ServiceCheckOperations;
import com.outfit7.fun7.service.user.api.UserInfoStorageOperations;
import com.outfit7.fun7.service.user.api.dto.FeatureState;
import com.outfit7.fun7.service.user.api.dto.UserFeatures;
import com.outfit7.fun7.service.user.api.dto.UserInfo;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class ServiceCheckService implements ServiceCheckOperations {

  private static final String US_COUNTRY_CODE = "US";

  private static final int NUMBER_OF_GAMES_PLAYED_TO_ENABLE_MULTIPLAYER = 5;

  private static final List<DayOfWeek> WORKDAYS = List.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY);

  private static final LocalTime SUPPORT_START_TIME = LocalTime.of(9, 0);

  private static final LocalTime SUPPORT_END_TIME = LocalTime.of(15, 0);

  private final UserInfoStorageOperations userInfoStorageOperations;

  private final Clock clock;

  public ServiceCheckService(UserInfoStorageOperations userInfoStorageOperations, Clock clock) {
    this.userInfoStorageOperations = userInfoStorageOperations;
    this.clock = clock;
  }

  @Override
  public UserFeatures getUserFeatures(String timezone, String userId, String countryCode) {
    FeatureState multiplayerFeatureState = getMultiplayerFeatureState(userId, countryCode);
    return UserFeatures.builder()
      .withMultiplayer(multiplayerFeatureState)
      .build();
  }

  private FeatureState getMultiplayerFeatureState(String userId, String countryCode) {
    UserInfo userInfo = userInfoStorageOperations.getUserInfo(userId);
    if (countryCode.equals(US_COUNTRY_CODE) && userInfo.getGameCount() > NUMBER_OF_GAMES_PLAYED_TO_ENABLE_MULTIPLAYER) {
      return FeatureState.ENABLED;
    }
    return FeatureState.DISABLED;
  }

  private FeatureState getSupportFeatureState(String timezone) {
    return isSupportAvailable();
  }

  private boolean isSupportAvailable() {
    LocalDateTime currentDateTime = LocalDateTime.now(clock);
    DayOfWeek currentDayOfWeek = currentDateTime.getDayOfWeek();
    LocalTime currentTime = currentDateTime.toLocalTime();

    boolean isWorkday = isWorkday(currentDayOfWeek);
    boolean isWithinSupportHours = isWithinSupportHours(currentTime);

    return isWorkday && isWithinSupportHours;
  }

  private boolean isWorkday(DayOfWeek dayOfWeek) {
    return WORKDAYS.contains(dayOfWeek);
  }

  private static boolean isWithinSupportHours(LocalTime currentTime) {
    return !currentTime.isBefore(SUPPORT_START_TIME) && !currentTime.isAfter(SUPPORT_END_TIME);
  }
}
