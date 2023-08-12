package com.usb.dictionary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableMongoRepositories(
    includeFilters = {@ComponentScan.Filter(type = FilterType.REGEX, pattern = ".*mongo.*")})
@EnableElasticsearchRepositories(
    includeFilters = {
      @ComponentScan.Filter(type = FilterType.REGEX, pattern = ".*elasticsearch.*")
    })
@EnableNeo4jRepositories(
    includeFilters = {@ComponentScan.Filter(type = FilterType.REGEX, pattern = ".*neo4j.*")})
@EnableWebSecurity
public class DictionaryApplication {

  public static void main(String[] args) {
    SpringApplication.run(DictionaryApplication.class, args);
  }
}
