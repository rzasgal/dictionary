package com.usb.dictionary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableElasticsearchRepositories(
    includeFilters = {
      @ComponentScan.Filter(type = FilterType.REGEX, pattern = ".*elasticsearch.*")
    })
@EnableNeo4jRepositories(
    includeFilters = {@ComponentScan.Filter(type = FilterType.REGEX, pattern = ".*neo4j.*")})
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class DictionaryApplication {

  public static void main(String[] args) {
    SpringApplication.run(DictionaryApplication.class, args);
  }
}
