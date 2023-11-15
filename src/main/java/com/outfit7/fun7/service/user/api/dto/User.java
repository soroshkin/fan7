package com.outfit7.fun7.service.user.api.dto;

public class User {

  private final String userId;

  private final int gameCount;

  public User(String userId, int gameCount) {
    this.userId = userId;
    this.gameCount = gameCount;
  }

  public String getUserId() {
    return userId;
  }

  public int getGameCount() {
    return gameCount;
  }

  @Override
  public String toString() {
    return "UserInfo{" +
      "userId='" + userId + '\'' +
      ", gameCount=" + gameCount +
      '}';
  }
}
