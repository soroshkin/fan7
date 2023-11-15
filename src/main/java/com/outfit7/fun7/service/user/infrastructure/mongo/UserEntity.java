package com.outfit7.fun7.service.user.infrastructure.mongo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("users")
public class UserEntity {

  @Id
  private final String userId;

  private final int gameCount;

  public UserEntity(String userId, int gameCount) {
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
    return "UserInfoEntity{" +
      "userId='" + userId + '\'' +
      ", gameCount=" + gameCount +
      '}';
  }
}
