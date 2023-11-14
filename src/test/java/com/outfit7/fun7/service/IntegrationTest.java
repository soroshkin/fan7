package com.outfit7.fun7.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xebialabs.restito.server.StubServer;
import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = {Application.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class IntegrationTest {

  @Autowired
  protected MongoTemplate mongoTemplate;

  @Autowired
  protected ObjectMapper objectMapper;

  @Autowired
  protected TestRestTemplate testRestTemplate;

  protected StubServer stubServer;

  @Value("${stub.server.port}")
  private Integer serverPort;

  @PostConstruct
  void init() {
    this.stubServer = new StubServer(serverPort);
  }

  @BeforeEach
  void setUp() {
    stubServer.run();
  }

  @AfterEach
  void tearDown() {
    stubServer.stop();
  }
}




