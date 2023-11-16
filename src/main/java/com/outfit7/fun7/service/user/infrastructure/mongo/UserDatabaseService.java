package com.outfit7.fun7.service.user.infrastructure.mongo;

import com.outfit7.fun7.service.user.api.UserStorageOperations;
import com.outfit7.fun7.service.user.api.dto.User;
import com.outfit7.fun7.service.user.api.dto.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
class UserDatabaseService implements UserStorageOperations {

  private final Logger logger = LoggerFactory.getLogger(getClass());

  private static final String USER_INFO_NOT_FOUND_ERROR_MESSAGE = "user is not found with userId %s";

  private final UserRepository userRepository;

  private final UserConverter userConverter;

  UserDatabaseService(UserRepository userRepository, UserConverter userConverter) {
    this.userRepository = userRepository;
    this.userConverter = userConverter;
  }

  @Override
  public List<User> getAllUsers() {
    return userRepository.findAll()
      .stream()
      .map(userEntity -> new User(userEntity.getId(), userEntity.getUserId(), userEntity.getGameCount()))
      .toList();
  }

  @Override
  public User getUser(String userId) {
    return userRepository
      .findByUserId(userId)
      .map(userConverter::toUser)
      .orElseThrow(() -> new UserNotFoundException(String.format(USER_INFO_NOT_FOUND_ERROR_MESSAGE, userId)));
  }

  @Override
  public User saveUser(User user) {
    UserEntity userEntity = userConverter.toEntity(user);
    UserEntity savedUserEntity = userRepository.save(userEntity);
    return userConverter.toUser(savedUserEntity);
  }

  @Override
  public String deleteUserById(String userId) {
    Optional<UserEntity> userEntityToDelete = userRepository.findByUserId(userId);

    if (userEntityToDelete.isEmpty()) {
      logger.warn("User with userId {} is not found", userId);
      throw new UserNotFoundException(String.format(USER_INFO_NOT_FOUND_ERROR_MESSAGE, userId));
    }
    userRepository.delete(userEntityToDelete.get());

    return userId;
  }
}
