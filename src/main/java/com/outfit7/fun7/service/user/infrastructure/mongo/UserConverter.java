package com.outfit7.fun7.service.user.infrastructure.mongo;

import com.outfit7.fun7.service.user.api.dto.User;
import org.springframework.stereotype.Component;

@Component
class UserConverter {

  UserEntity toEntity(User user) {
    return new UserEntity(user.getId(), user.getUserId(), user.getGameCount());
  }

  User toUser(UserEntity userEntity) {
    return new User(userEntity.getId(), userEntity.getUserId(), userEntity.getGameCount());
  }
}
