package com.outfit7.fun7.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

@SpringBootTest(classes = {Application.class})
public class IntegrationTest {

  @Autowired
  protected MongoTemplate mongoTemplate;

  @Autowired
  protected ObjectMapper objectMapper;
}




