package com.outfit7.fun7.service.user.infrastructure.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<UserEntity, String> {

  Optional<UserEntity> findByUserId(String userId);
}
