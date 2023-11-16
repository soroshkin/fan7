package com.outfit7.fun7.service.feature.infrastructure.ads;

import com.outfit7.fun7.service.feature.api.dto.FeignClientException;
import feign.FeignException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.lang.Nullable;

import java.io.IOException;
import java.util.Optional;

class FeignClientErrorDecoder implements ErrorDecoder {

  private final Logger logger = LoggerFactory.getLogger(getClass());

  private final ResponseEntityDecoder responseEntityDecoder;

  public FeignClientErrorDecoder(ResponseEntityDecoder responseEntityDecoder) {
    this.responseEntityDecoder = responseEntityDecoder;
  }

  @Override
  public Exception decode(String methodKey, Response response) {
    Optional<String> errorMessage = getErrorMessage(response);
    return new FeignClientException(response.status(), errorMessage.orElse(""));
  }

  private Optional<String> getErrorMessage(@Nullable Response response) {
    return Optional.ofNullable(response)
      .map(r -> {
        String errorResponse = null;
        try {
          errorResponse = (String) responseEntityDecoder.decode(r, String.class);
        } catch (IOException | FeignException e) {
          logger.warn("Failed to decode error response: {}", e.getMessage());
        }
        return errorResponse;
      });
  }
}
