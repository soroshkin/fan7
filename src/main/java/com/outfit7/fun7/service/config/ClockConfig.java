package com.outfit7.fun7.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;
import java.time.ZoneId;

@Configuration
public class ClockConfig {

  @Bean
  public Clock supportServiceClock() {
    return Clock.system(ZoneId.of("Europe/Ljubljana"));
  }
}
