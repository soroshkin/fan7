package com.outfit7.fun7.service.admin.infrastructure.rest;

import com.outfit7.fun7.service.RestIntegrationTest;
import com.outfit7.fun7.service.admin.UserService;
import com.outfit7.fun7.service.admin.api.dto.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.validation.ConstraintViolationException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserExceptionHandlerIntegrationTest extends RestIntegrationTest {

  private static final String USERS_URL = "/admin/api/users";

  @MockBean
  private UserService userService;

  @Test
  void shouldHandleUserNotFoundException() {
    // given
    when(userService.getAllUsers()).thenThrow(new UserNotFoundException("User not found"));

    // when
    ResponseEntity<String> response = testRestTemplate.getForEntity(USERS_URL, String.class);

    // then
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    assertThat(response.getBody()).isEqualTo("User not found");
    verify(userService).getAllUsers();
  }

  @Test
  void shouldHandleConstraintViolationException() {
    // given
    when(userService.getAllUsers()).thenThrow(new ConstraintViolationException("Validation failed", null));

    // when
    ResponseEntity<String> response = testRestTemplate.getForEntity(USERS_URL, String.class);

    // then
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(response.getBody()).isEqualTo("Validation failed");
    verify(userService).getAllUsers();
  }
}