package com.outfit7.fun7.service.admin.api;

import com.outfit7.fun7.service.admin.infrastructure.mongo.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserStorageOperations {

  List<UserEntity> getAllUsers();

  Optional<UserEntity> getUserById(String userId);

  String deleteUserById(String userId);
}