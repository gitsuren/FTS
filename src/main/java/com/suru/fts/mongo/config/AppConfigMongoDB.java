package com.suru.fts.mongo.config;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

@Configuration
@ComponentScan(basePackages = { "com.suru.fts.*" })
@PropertySource("classpath:bootstrap.properties")
public class AppConfigMongoDB {

    @Value("${mongodb.url}")
    private String mongodbUrl;

    //hello
    @Value("${mongodb.db}")
    private String defaultDb;

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {

        MongoClientOptions mongoOptions =
                new MongoClientOptions.Builder().maxWaitTime(1000 * 60 * 5).build();
        MongoClient mongo = new MongoClient(mongodbUrl, mongoOptions);
        MongoDbFactory mongoDbFactory = new SimpleMongoDbFactory(mongo, defaultDb);
        return new MongoTemplate(mongoDbFactory);

    }

    //To resolve ${} in @Value
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
