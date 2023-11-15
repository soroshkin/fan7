package com.outfit7.fun7.service.user.infrastructure.rest;

import com.outfit7.fun7.service.user.api.UserOperations;
import com.outfit7.fun7.service.user.api.dto.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import java.util.List;

@RestController
@Validated
@RequestMapping("/admin/api/users")
@PreAuthorize("hasRole('ADMIN')")
class UserController {

  private final Logger logger = LoggerFactory.getLogger(getClass());

  private final UserOperations userOperations;

  UserController(UserOperations userOperations) {
    this.userOperations = userOperations;
  }

  @GetMapping
  public List<User> getAllUsers() {
    logger.info("Returning all users");
    return userOperations.getAllUsers();
  }

  @GetMapping("/{userId}")
  public User getUserDetails(@PathVariable @NotBlank String userId) {
    logger.info("Returning user with id {}", userId);
    return userOperations.getUserById(userId);
  }

  @DeleteMapping("/{userId}")
  public String deleteUser(@PathVariable @NotBlank String userId) {
    logger.info("About to delete user with id {}", userId);
    String user = userOperations.deleteUser(userId);
    logger.info("User with id {} has been deleted", userId);
    return user;
  }
}