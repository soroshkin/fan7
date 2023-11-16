package com.outfit7.fun7.service;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.repository.init.Jackson2ResourceReader;
import org.springframework.data.repository.init.ResourceReaderRepositoryPopulator;
import org.springframework.data.repository.support.Repositories;

public class NeedsTestsDataIntegrationTest extends IntegrationTest {

  @Autowired
  private ApplicationContext applicationContext;

  @BeforeEach
  void tearDown() {
    for (String collectionName : mongoTemplate.getCollectionNames()) {
      if (!collectionName.startsWith("system")) {
        mongoTemplate.dropCollection(collectionName);
      }
    }
  }

  protected void populateDatabase(String path) {
    ResourceReaderRepositoryPopulator populator = new ResourceReaderRepositoryPopulator(new Jackson2ResourceReader(objectMapper));
    populator.setResources(new ClassPathResource(path));
    populator.populate(new Repositories(applicationContext));
  }
}

