package com.usb.dictionary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
@EnableElasticsearchRepositories
public class DictionaryApplication {

	public static void main(String[] args) {
		SpringApplication.run(DictionaryApplication.class, args);
	}

}
