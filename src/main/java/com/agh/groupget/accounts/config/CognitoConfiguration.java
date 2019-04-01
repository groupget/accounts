package com.agh.groupget.accounts.config;

import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class CognitoConfiguration {

    private static final String REGION = "eu-central-1";

    @Bean
    AWSCognitoIdentityProvider awsCognitoIdentityProvider() {
        ClasspathPropertiesFileCredentialsProvider propertiesFileCredentialsProvider = new ClasspathPropertiesFileCredentialsProvider();
        return AWSCognitoIdentityProviderClientBuilder.standard()
                .withCredentials(propertiesFileCredentialsProvider)
                .withRegion(REGION)
                .build();
    }
}
