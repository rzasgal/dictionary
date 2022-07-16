package com.usb.dictionary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableMongoRepositories(includeFilters = {
		@ComponentScan.Filter(type = FilterType.REGEX, pattern = ".*mongo.*")
})
@EnableElasticsearchRepositories(includeFilters = {
		@ComponentScan.Filter(type = FilterType.REGEX, pattern = ".*elasticsearch.*")
})
@EnableWebSecurity
public class DictionaryApplication {

	public static void main(String[] args) {
		SpringApplication.run(DictionaryApplication.class, args);
	}

}
