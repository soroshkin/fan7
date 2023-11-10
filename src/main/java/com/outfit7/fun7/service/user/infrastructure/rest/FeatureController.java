package com.outfit7.fun7.service.user.infrastructure.rest;

import com.outfit7.fun7.service.user.api.ServiceCheckOperations;
import com.outfit7.fun7.service.user.api.dto.UserFeatures;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
class FeatureController {

  private final ServiceCheckOperations serviceCheckOperations;

  FeatureController(ServiceCheckOperations serviceCheckOperations) {
    this.serviceCheckOperations = serviceCheckOperations;
  }

  @GetMapping(path = "/features/state")
  UserFeatures getUserFeatureState(@RequestParam String timezone,
                                   @RequestParam String userId,
                                   @RequestParam(name = "cc") String countryCode) {
    return serviceCheckOperations.getUserFeatures(timezone, userId, countryCode);
  }
}
