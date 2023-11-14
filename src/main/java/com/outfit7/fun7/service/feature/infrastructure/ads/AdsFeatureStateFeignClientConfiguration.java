package com.outfit7.fun7.service.feature.infrastructure.ads;

import feign.Feign;
import feign.RequestInterceptor;
import feign.Retryer;
import feign.okhttp.OkHttpClient;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.HttpMessageConverterCustomizer;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class AdsFeatureStateFeignClientConfiguration {

  private static final int DEFAULT_RETRYER_PERIOD = 100;

  private static final int DEFAULT_RETRYER_MAX_PERIOD = 1000;

  private static final int DEFAULT_MAX_ATTEMPTS = 5;

  @Bean
  @ConditionalOnProperty({"fun7.o7tools.api.base-url"})
  AdsFeatureStateFeignClient adsFeatureStateFeignClient(@Value("${fun7.o7tools.api.base-url}") String baseUrl,
                                                        ObjectFactory<HttpMessageConverters> messageConverters,
                                                        ObjectProvider<HttpMessageConverterCustomizer> messageConverterCustomizers,
                                                        RequestInterceptor authAdminHttpRequestInterceptor) {
    ResponseEntityDecoder decoder = new ResponseEntityDecoder(new SpringDecoder(messageConverters, messageConverterCustomizers));
    return Feign.builder()
      .encoder(new SpringEncoder(messageConverters))
      .decoder(decoder)
      .errorDecoder(new FeignClientErrorDecoder(decoder))
      .requestInterceptor(authAdminHttpRequestInterceptor)
      .client(new OkHttpClient())
      .retryer(new Retryer.Default(DEFAULT_RETRYER_PERIOD, DEFAULT_RETRYER_MAX_PERIOD, DEFAULT_MAX_ATTEMPTS))
      .target(AdsFeatureStateFeignClient.class, baseUrl);
  }

  @Bean
  public RequestInterceptor authAdminHttpRequestInterceptor(@Value("${fun7.o7tools.api.username}") String username, @Value("${fun7.o7tools.api.password}") String password) {
    return new AuthAdminHttpRequestInterceptor(username, password);
  }
}