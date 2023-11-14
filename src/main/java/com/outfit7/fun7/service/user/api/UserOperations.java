package com.outfit7.fun7.service.user.api;

import com.outfit7.fun7.service.user.api.dto.User;

import java.util.List;

public interface UserOperations {

  List<User> getAllUsers();

  User getUserById(String userId);

  String deleteUser(String user);
}