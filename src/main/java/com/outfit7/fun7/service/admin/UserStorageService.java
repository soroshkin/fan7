package com.outfit7.fun7.service.admin;

import com.outfit7.fun7.service.admin.api.UserStorageOperations;
import com.outfit7.fun7.service.admin.api.dto.UserNotFoundException;
import com.outfit7.fun7.service.admin.infrastructure.mongo.UserEntity;
import com.outfit7.fun7.service.admin.infrastructure.mongo.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserStorageService implements UserStorageOperations {

  private static final String USER_NOT_FOUND_ERROR_MESSAGE = "user is not found with userId %s";

  private final UserRepository userRepository;

  public UserStorageService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public List<UserEntity> getAllUsers() {
    return userRepository.findAll();
  }

  @Override
  public Optional<UserEntity> getUserById(String userId) {
    return userRepository.findById(userId);
  }

  @Override
  public String deleteUserById(String userId) {
    if (!userRepository.existsById(userId)) {
      throw new UserNotFoundException(String.format(USER_NOT_FOUND_ERROR_MESSAGE, userId));
    }
    userRepository.deleteById(userId);

    return userId;
  }
}
