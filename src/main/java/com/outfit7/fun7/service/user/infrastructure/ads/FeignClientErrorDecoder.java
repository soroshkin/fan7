package com.outfit7.fun7.service.user.infrastructure.ads;

import com.outfit7.fun7.service.user.api.dto.FeignClientException;
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
    Optional<ErrorMessage> errorMessage = getErrorMessage(response);
    return new FeignClientException(response.status(),
      errorMessage.map(ErrorMessage::getErrorCode).orElse(""),
      errorMessage.map(ErrorMessage::getMessage).orElse(""));
  }

  private Optional<ErrorMessage> getErrorMessage(@Nullable Response response) {
    return Optional.ofNullable(response)
      .map(r -> {
        ErrorResponse errorResponse = null;
        try {
          errorResponse = (ErrorResponse) responseEntityDecoder.decode(r, ErrorResponse.class);
        } catch (IOException | FeignException e) {
          logger.warn("Failed to decode error response: {}", e.getMessage());
        }
        return errorResponse;
      })
      .flatMap(errorResponse -> Optional.ofNullable(errorResponse.getErrorMessages())
        .flatMap(errorMessages -> errorMessages.stream().findAny()));
  }
}
