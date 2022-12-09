package com.otp.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration @Setter @Getter
@ConfigurationProperties(prefix = "spring.redis")
public class ApplicationProperties {

    private String host;

    private int port;

    private String username;

    private String password;

    private String chanelName;

}
