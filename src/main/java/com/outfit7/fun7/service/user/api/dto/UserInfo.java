package com.outfit7.fun7.service.user.api.dto;

public class UserInfo {

  private final String userId;

  private final String countryCode;

  private final int gameCount;

  public UserInfo(String userId, String countryCode, int gameCount) {
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
    return "UserInfo{" +
      "userId='" + userId + '\'' +
      ", countryCode='" + countryCode + '\'' +
      ", gameCount=" + gameCount +
      '}';
  }
}
