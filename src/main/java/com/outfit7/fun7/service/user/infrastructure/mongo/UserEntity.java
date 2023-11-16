package com.outfit7.fun7.service.user.infrastructure.mongo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("users")
class UserEntity {

  @Id
  private final String id;

  @Indexed(unique = true)
  private final String userId;

  private final int gameCount;

  UserEntity(String id, String userId, int gameCount) {
    this.id = id;
    this.userId = userId;
    this.gameCount = gameCount;
  }

  String getId() {
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
    return "UserEntity{" +
      "id='" + id + '\'' +
      ", userId='" + userId + '\'' +
      ", gameCount=" + gameCount +
      '}';
  }
}
