package com.outfit7.fun7.service.feature.infrastructure.ads;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.http.HttpHeaders;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

class AuthAdminHttpRequestInterceptor implements RequestInterceptor {

  private final String username;

  private final String password;

  AuthAdminHttpRequestInterceptor(String username, String password) {
    this.username = username;
    this.password = password;
  }

  @Override
  public void apply(RequestTemplate template) {
    String credentials = username + ":" + password;
    String base64Credentials = Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));
    String authorizationHeader = "Basic " + base64Credentials;
    template.header(HttpHeaders.AUTHORIZATION, authorizationHeader);
  }
}