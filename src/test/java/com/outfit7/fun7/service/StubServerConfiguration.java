package com.outfit7.fun7.service;

import com.xebialabs.restito.server.StubServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import java.util.Objects;

@Profile("test")
public class StubServerConfiguration {

  @Bean
  public StubServer stubServer(@Autowired ApplicationContext applicationContext) {
    int stubServerPort = Integer.parseInt(Objects.requireNonNull(applicationContext.getEnvironment().getProperty("stub.server.port")));
    return new StubServer(stubServerPort).run();
  }
}
