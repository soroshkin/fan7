package com.outfit7.fun7.service.user.infrastructure.mongo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("userInfo")
public class UserInfoEntity {

  @Id
  private final String userId;

  private final String countryCode;

  private final int gameCount;

  public UserInfoEntity(String userId, String countryCode, int gameCount) {
    this.userId = userId;
    this.countryCode = countryCode;
    this.gameCount = gameCount;
  }

  public String getUserId() {
    return userId;
  }

  public String getCountryCode() {
    return countryCode;
  }

  public int getGameCount() {
    return gameCount;
  }

  @Override
  public String toString() {
    return "UserInfoEntity{" +
      "userId='" + userId + '\'' +
      ", countryCode='" + countryCode + '\'' +
      ", gameCount=" + gameCount +
      '}';
  }
}
