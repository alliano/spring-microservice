package com.email.configurations;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration @Setter @Getter
@ConfigurationProperties(prefix = "spring.redis")
public class ApplicationProperties {

    private int port;

    private String host;

    private String username;

    private String password;

    private int redisDatabase;

    private String chanelName;
}
