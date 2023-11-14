package com.outfit7.fun7.service.admin.api;

import com.outfit7.fun7.service.admin.api.dto.User;

import java.util.List;
import java.util.Optional;

public interface UserOperations {

  List<User> getAllUsers();

  Optional<User> getUserById(String userId);

  String deleteUser(String userId);
}