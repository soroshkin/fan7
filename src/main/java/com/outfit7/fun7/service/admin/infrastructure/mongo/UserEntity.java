package com.outfit7.fun7.service.admin.infrastructure.mongo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class UserEntity {

  @Id
  private final String id;

  private final String username;

  @Indexed(name = "email_index", unique = true)
  private final String email;

  public UserEntity(String id, String username, String email) {
    this.id = id;
    this.username = username;
    this.email = email;
  }

  public String getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }

  public String getEmail() {
    return email;
  }

  @Override
  public String toString() {
    return "UserEntity{" +
      "id='" + id + '\'' +
      ", username='" + username + '\'' +
      ", email='" + email + '\'' +
      '}';
  }
}