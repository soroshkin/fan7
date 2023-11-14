package com.outfit7.fun7.service.user.infrastructure.rest;

import com.outfit7.fun7.service.RestIntegrationTest;
import com.outfit7.fun7.service.user.UserService;
import com.outfit7.fun7.service.user.api.dto.User;
import com.outfit7.fun7.service.user.api.dto.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

class UserControllerIntegrationTest extends RestIntegrationTest {

  private static final String ADMIN_USERNAME = "admin";

  private static final String ADMIN_PASSWORD = "admin";

  private static final String USERS_URL = "/admin/api/users/";

  @MockBean
  private UserService userService;

  @Test
  void shouldGetUsers() {
    // given
    User firstUser = new User("111", "US", 3);
    User secondUser = new User("222", "FR", 4);
    List<User> users = List.of(firstUser, secondUser);
    when(userService.getAllUsers()).thenReturn(users);

    // when
    ResponseEntity<List<User>> response = testRestTemplate
      .withBasicAuth(ADMIN_USERNAME, ADMIN_PASSWORD)
      .exchange(USERS_URL,
        HttpMethod.GET,
        null,
        new ParameterizedTypeReference<>() {
        });

    // then
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).usingRecursiveComparison().isEqualTo(users);
  }

  @Test
  void shouldNotGetUsersWhenInvalidCredentialsAreProvided() {
    // when
    ResponseEntity<String> response = testRestTemplate
      .withBasicAuth(ADMIN_USERNAME, "wrong_password")
      .exchange(USERS_URL,
        HttpMethod.GET,
        null,
        String.class);

    // then
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    verifyNoInteractions(userService);
  }

  @Test
  void shouldNotGetUsersWhenUserDoesNotHaveRequiredRole() {
    // when
    ResponseEntity<String> response = testRestTemplate
      .withBasicAuth("user", "user")
      .exchange(USERS_URL,
        HttpMethod.GET,
        null,
        String.class);

    // then
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    verifyNoInteractions(userService);
  }

  @Test
  void shouldNotGetUsersWhenNoCredentialsAreProvided() {
    // when
    ResponseEntity<String> response = testRestTemplate
      .exchange(USERS_URL,
        HttpMethod.GET,
        null,
        String.class);

    // then
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    verifyNoInteractions(userService);
  }

  @Test
  void shouldGetUserById() {
    // given
    String userId = "111";
    User givenUser = new User("111", "US", 3);
    when(userService.getUserById(userId)).thenReturn(givenUser);

    // when
    ResponseEntity<User> response = testRestTemplate
      .withBasicAuth(ADMIN_USERNAME, ADMIN_PASSWORD)
      .getForEntity(USERS_URL + userId, User.class);

    // then
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    User user = response.getBody();
    assertThat(user).isNotNull().usingRecursiveComparison().isEqualTo(givenUser);
  }

  @Test
  void shouldNotReturnUserWhenItIsNotFound() {
    // given
    String userId = "2323";
    when(userService.getUserById(anyString())).thenThrow(new UserNotFoundException("User not found"));

    // when
    ResponseEntity<String> response = testRestTemplate
      .withBasicAuth(ADMIN_USERNAME, ADMIN_PASSWORD)
      .getForEntity(USERS_URL + userId, String.class);

    // then
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    assertThat(response.getBody()).isEqualTo("User not found");
    verify(userService).getUserById(userId);
  }

  @Test
  void shouldDeleteUser() {
    // given
    String userId = "2323";
    when(userService.deleteUser(userId)).thenReturn(userId);

    // when
    ResponseEntity<String> response = testRestTemplate
      .withBasicAuth(ADMIN_USERNAME, ADMIN_PASSWORD)
      .exchange(USERS_URL + userId,
        HttpMethod.DELETE,
        null,
        String.class);

    // then
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isEqualTo(userId);
    verify(userService).deleteUser(userId);
  }
}