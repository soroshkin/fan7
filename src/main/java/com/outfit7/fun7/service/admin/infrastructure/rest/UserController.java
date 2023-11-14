package com.outfit7.fun7.service.admin.infrastructure.rest;

import com.outfit7.fun7.service.admin.UserService;
import com.outfit7.fun7.service.admin.api.dto.User;
import com.outfit7.fun7.service.admin.api.dto.UserNotFoundException;
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

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping
  public List<User> getAllUsers() {
    return userService.getAllUsers();
  }

  @GetMapping("/{userId}")
  public User getUserDetails(@PathVariable @NotBlank String userId) {
    return userService.getUserById(userId)
      .orElseThrow(() -> new UserNotFoundException(String.format("User with id %s not found", userId)));
  }

  @DeleteMapping("/{userId}")
  public String deleteUser(@PathVariable @NotBlank String userId) {
    return userService.deleteUser(userId);
  }
}