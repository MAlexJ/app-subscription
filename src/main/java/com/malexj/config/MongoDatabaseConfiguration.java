package com.malexj.config;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.ReactiveMongoDatabaseFactory;
import org.springframework.data.mongodb.ReactiveMongoTransactionManager;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.config.EnableReactiveMongoAuditing;

/**
 * Configuration Mongo database:
 *
 * <p>Reactive MongoDB documentation <br>
 * <a
 * href="https://docs.spring.io/spring-data/mongodb/docs/2.0.0.RC2/reference/html/#mongo.reactive.repositories">Reactive
 * MongoDB repositories</a>
 */
@Slf4j
@Configuration
@EnableReactiveMongoAuditing
@RequiredArgsConstructor
public class MongoDatabaseConfiguration extends AbstractReactiveMongoConfiguration {
  private final MongoProperties mongoProperties;

  /**
   * Transactional Mongo DB & Spring Boot <br>
   * link: <a
   * href="https://stackoverflow.com/questions/56360094/calling-methods-in-two-different-reactivemongorepositorys-in-a-transaction-usin/61676211#61676211">Transactional</a>
   */
  @Bean
  public ReactiveMongoTransactionManager transactionManager(
      ReactiveMongoDatabaseFactory dbFactory) {
    log.info("MongoDb configuration reactive transaction manager");
    return new ReactiveMongoTransactionManager(dbFactory);
  }

  /**
   * Spring Data MongoDB reference documentation <br>
   * link: <a href="https://docs.spring.io/spring-data/mongodb/reference/index.html">Spring Data
   * MongoDB</a>
   */
  @Override
  protected String getDatabaseName() {
    String database = Objects.requireNonNull(mongoProperties.getDatabase());
    log.info("MongoDb configuration database properties: {}", database);
    return mongoProperties.getDatabase();
  }

  @Override
  public MongoClient reactiveMongoClient() {
    String uri = Objects.requireNonNull(mongoProperties.getUri());
    log.info("MongoDb configuration URI property: {}", uri);
    return MongoClients.create(uri);
  }
}
