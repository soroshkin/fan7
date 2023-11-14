package com.outfit7.fun7.service.admin;

import com.outfit7.fun7.service.admin.api.UserOperations;
import com.outfit7.fun7.service.admin.api.UserStorageOperations;
import com.outfit7.fun7.service.admin.api.dto.User;
import com.outfit7.fun7.service.admin.infrastructure.mongo.UserEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserOperations {

  private final UserStorageOperations userStorageOperations;

  public UserService(UserStorageOperations userStorageOperations) {
    this.userStorageOperations = userStorageOperations;
  }

  @Override
  public List<User> getAllUsers() {
    return userStorageOperations.getAllUsers().stream()
      .map(userEntity -> new User(userEntity.getId(), userEntity.getUsername(), userEntity.getEmail()))
      .toList();
  }

  @Override
  public Optional<User> getUserById(String userId) {
    return userStorageOperations.getUserById(userId)
      .map(this::toUser);
  }

  @Override
  public String deleteUser(String userId) {
    return userStorageOperations.deleteUserById(userId);
  }

  private User toUser(UserEntity userEntity) {
    return new User(userEntity.getId(), userEntity.getUsername(), userEntity.getEmail());
  }
}
