package com.outfit7.fun7.service.admin.infrastructure.rest;

import com.outfit7.fun7.service.admin.api.UserOperations;
import com.outfit7.fun7.service.user.api.dto.User;
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
public class UserController {

  private final UserOperations userOperations;

  public UserController(UserOperations userOperations) {
    this.userOperations = userOperations;
  }

  @GetMapping
  public List<User> getAllUsers() {
    return userOperations.getAllUsers();
  }

  @GetMapping("/{userId}")
  public User getUserDetails(@PathVariable @NotBlank String userId) {
    return userOperations.getUserById(userId);
  }

  @DeleteMapping("/{userId}")
  public String deleteUser(@PathVariable @NotBlank String userId) {
    return userOperations.deleteUser(userId);
  }
}