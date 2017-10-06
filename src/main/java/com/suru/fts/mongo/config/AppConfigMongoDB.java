package com.suru.fts.mongo.config;

import com.mongodb.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.authentication.UserCredentials;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import java.net.UnknownHostException;
import java.util.ArrayList;

@Configuration
@ComponentScan(basePackages = { "com.suru.fts.*" })
@PropertySource("classpath:bootstrap.properties")
public class AppConfigMongoDB {

    @Value("${mongodb.name}")
    private String  dbName;

    @Value("${mongodb.host}")
    private String  host;

    @Value("${mongodb.port}")
    private Integer port;

    @Value("${mongodb.username}")
    private String  userName;

    @Value("${mongodb.password}")
    private String  password;


public @Bean MongoClient mongoClient() throws UnknownHostException {
    return new MongoClient(new ServerAddress(host, port), new ArrayList<MongoCredential>() {

        {
            add(MongoCredential.createCredential(userName, dbName, password.toCharArray()));
        }
    });
}

    public @Bean MongoDbFactory mongoDbFactory() throws Exception {
        return new SimpleMongoDbFactory(mongoClient(), dbName);
    }

    public @Bean MongoTemplate mongoTemplate() throws Exception {
        MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory());
        return mongoTemplate;
    }

}
