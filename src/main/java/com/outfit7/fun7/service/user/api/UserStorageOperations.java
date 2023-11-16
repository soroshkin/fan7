package com.outfit7.fun7.service.user.api;

import com.outfit7.fun7.service.user.api.dto.User;

import java.util.List;

public interface UserStorageOperations {

  User getUser(String userId);

  User saveUser(User userEntity);

  String deleteUserById(String userId);

  List<User> getAllUsers();
}
