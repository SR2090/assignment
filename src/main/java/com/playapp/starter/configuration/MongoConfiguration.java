package com.playapp.starter.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


@Configuration
@EnableMongoRepositories(basePackages = "com.playapp.starter.repository")
public class MongoConfiguration extends AbstractMongoClientConfiguration{
    @Override
    protected boolean autoIndexCreation() {
        return true;
    }
    @Override
    protected String getDatabaseName() {
        // TODO Auto-generated method stub
        return "PlayApp";
    }
}