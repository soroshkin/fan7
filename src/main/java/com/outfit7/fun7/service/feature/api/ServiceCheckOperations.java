package com.outfit7.fun7.service.feature.api;

import com.outfit7.fun7.service.feature.api.dto.UserFeatures;

public interface ServiceCheckOperations {

  UserFeatures getUserFeatures(String timezone, String userId, String countryCode);
}
