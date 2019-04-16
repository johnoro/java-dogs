package com.lambdaschool.dogs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j // auto-creates Slf4j-based logs
@Configuration // indicates that a class declares one or more @Beans
public class SeedDatabase {
  @Bean
  // CommandLineRunner - Spring Boot Runs all Beans at startup
  public CommandLineRunner initializeDatabase(DogRepository repository) {
    return args -> {
      log.info("Seeding " + repository.save(
        new Dog("Springer", 50, false)
      ));
      log.info("Seeding " + repository.save(
        new Dog("Bulldog", 50, true)
      ));
      log.info("Seeding " + repository.save(
        new Dog("Collie", 50, false)
      ));
      log.info("Seeding " + repository.save(
        new Dog("Boston Terrie", 35, true)
      ));
      log.info("Seeding " + repository.save(
        new Dog("Corgi", 35, true)
      ));
    };
  }
}
