package com.agh.groupget.accounts.config;

import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableDynamoDBRepositories(basePackages = "com.agh.groupget.accounts.domain")
class DynamoDBConfiguration {

    @Bean
    AmazonDynamoDB amazonDynamoDB() {
        return AmazonDynamoDBClientBuilder.standard()
                                          .withRegion(Regions.EU_CENTRAL_1)
                                          .withCredentials(new EnvironmentVariableCredentialsProvider())
                                          .build();
    }
}
