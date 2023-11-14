package com.outfit7.fun7.service.admin.infrastructure.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<UserEntity, String> {

}