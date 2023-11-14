package com.outfit7.fun7.service.feature;

import com.outfit7.fun7.service.feature.api.dto.FeatureState;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
class SupportFeatureService {

  private static final LocalTime SUPPORT_START_TIME = LocalTime.of(9, 0);

  private static final LocalTime SUPPORT_END_TIME = LocalTime.of(15, 0);

  private static final List<DayOfWeek> WORKDAYS = List.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY);

  private final Clock supportServiceClock;

  SupportFeatureService(Clock supportServiceClock) {
    this.supportServiceClock = supportServiceClock;
  }

  FeatureState getSupportFeatureState() {
    return isSupportAvailable() ? FeatureState.ENABLED : FeatureState.DISABLED;
  }

  private boolean isSupportAvailable() {
    LocalDateTime currentDateTime = LocalDateTime.now(supportServiceClock);
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