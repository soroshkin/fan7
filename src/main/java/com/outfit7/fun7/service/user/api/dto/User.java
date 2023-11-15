package com.outfit7.fun7.service.user.api.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

public class User {

  private final String id;

  private final String userId;

  private final int gameCount;

  public User(String userId, int gameCount) {
    this(null, userId, gameCount);
  }

  @JsonCreator
  public User(String id, String userId, int gameCount) {
    this.id = id;
    this.userId = userId;
    this.gameCount = gameCount;
  }

  public String getId() {
    return id;
  }

  public String getUserId() {
    return userId;
  }

  public int getGameCount() {
    return gameCount;
  }

  @Override
  public String toString() {
    return "User{" +
      "id='" + id + '\'' +
      ", userId='" + userId + '\'' +
      ", gameCount=" + gameCount +
      '}';
  }
}
