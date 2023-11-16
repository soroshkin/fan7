package com.outfit7.fun7.service.feature.infrastructure.rest;

import com.outfit7.fun7.service.feature.api.ServiceCheckOperations;
import com.outfit7.fun7.service.feature.api.dto.UserFeatures;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

  private final Logger logger = LoggerFactory.getLogger(getClass());

  private final ServiceCheckOperations serviceCheckOperations;

  FeatureController(ServiceCheckOperations serviceCheckOperations) {
    this.serviceCheckOperations = serviceCheckOperations;
  }

  @GetMapping(path = "/features/state")
  UserFeatures getUserFeatureState(@RequestParam(required = false) String timezone,
                                   @RequestParam @NotBlank String userId,
                                   @RequestParam(name = "cc") @NotBlank String countryCode) {
    logger.info("Retrieving feature states for user with id = {}", userId);
    return serviceCheckOperations.getUserFeatures(timezone, userId, countryCode);
  }
}
