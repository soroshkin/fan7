package com.outfit7.fun7.service.feature.infrastructure.rest;

import com.outfit7.fun7.service.feature.api.ServiceCheckOperations;
import com.outfit7.fun7.service.feature.api.dto.UserFeatures;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;

@RestController
@Validated
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
class FeatureController {

  private final ServiceCheckOperations serviceCheckOperations;

  FeatureController(ServiceCheckOperations serviceCheckOperations) {
    this.serviceCheckOperations = serviceCheckOperations;
  }

  @GetMapping(path = "/features/state")
  UserFeatures getUserFeatureState(@RequestParam @NotBlank String timezone,
                                   @RequestParam @NotBlank String userId,
                                   @RequestParam(name = "cc") @NotBlank String countryCode) {
    return serviceCheckOperations.getUserFeatures(timezone, userId, countryCode);
  }
}
