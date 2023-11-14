package com.outfit7.fun7.service.admin;

import com.outfit7.fun7.service.admin.api.UserOperations;
import com.outfit7.fun7.service.user.api.UserStorageOperations;
import com.outfit7.fun7.service.user.api.dto.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserOperations {

  private final UserStorageOperations userStorageOperations;

  public UserService(UserStorageOperations userStorageOperations) {
    this.userStorageOperations = userStorageOperations;
  }

  @Override
  public List<User> getAllUsers() {
    return userStorageOperations.getAllUsers();
  }

  @Override
  public User getUserById(String userId) {
    return userStorageOperations.getUser(userId);
  }

  @Override
  public String deleteUser(String userId) {
    return userStorageOperations.deleteUserById(userId);
  }
}
