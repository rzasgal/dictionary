package com.usb.dictionary.entry.configuration;

import com.mongodb.Block;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.connection.ClusterSettings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

import static java.util.Collections.singletonList;

@Configuration
public class MongoConfiguration {

    @Bean
    public MongoDatabaseFactory mongoDbFactory() {
        MongoCredential mongoCredential = MongoCredential
                .createCredential("dictuser", "dictionary", "test".toCharArray());
        Block<ClusterSettings.Builder> localhost = builder -> builder
                .hosts(singletonList(new ServerAddress("127.0.0.1", 27017)));
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder().applyToClusterSettings(localhost)
                .credential(mongoCredential).build();
        MongoClient mongoClient = MongoClients.create(mongoClientSettings);
        return new SimpleMongoClientDatabaseFactory(mongoClient, "dictionary");
    }
}
