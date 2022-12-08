package com.account.accountservice.configurations;

import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration @Setter @Getter
@org.springframework.boot.context.properties.ConfigurationProperties(prefix = "spring.redis")
public class ConfigurationProperties {

    private String host;

    private int port;

    private String username;

    private String password;

    private int redisDatabase;
}
