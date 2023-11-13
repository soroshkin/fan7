package com.outfit7.fun7.service.user.infrastructure.rest;

import com.outfit7.fun7.service.IntegrationTest;
import com.outfit7.fun7.service.user.api.ServiceCheckOperations;
import com.outfit7.fun7.service.user.api.dto.FeatureState;
import com.outfit7.fun7.service.user.api.dto.UserFeatures;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
class FeatureControllerIntegrationTest extends IntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private ServiceCheckOperations serviceCheckOperations;

  @Test
  void getUserFeatureState() throws Exception {
    // given
    UserFeatures givenUserFeatures = UserFeatures.builder()
      .withMultiplayer(FeatureState.ENABLED)
      .withUserSupport(FeatureState.ENABLED)
      .withAds(FeatureState.ENABLED)
      .build();
    when(serviceCheckOperations.getUserFeatures(anyString(), anyString(), anyString()))
      .thenReturn(givenUserFeatures);

    // when - then
    mockMvc.perform(get("/features/state")
        .param("timezone", "UTC")
        .param("userId", "123")
        .param("cc", "US"))
      .andExpect(status().isOk())
      .andExpect(content().contentType("application/json"))
      .andExpect(jsonPath("$.multiplayer").value("enabled"))
      .andExpect(jsonPath("$.ads").value("enabled"))
      .andExpect(jsonPath("$.user-support").value("enabled"))
      .andReturn();
  }
}

