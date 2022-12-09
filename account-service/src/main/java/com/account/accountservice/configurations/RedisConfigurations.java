package com.account.accountservice.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import feign.Logger;
import lombok.AllArgsConstructor;

@Configuration @AllArgsConstructor
@EnableRedisRepositories
public class RedisConfigurations {

    private final ConfigurationProperties configurationProperties;

    @Bean
    public LettuceConnectionFactory redisConnection(){
        RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration();
        redisConfig.setHostName(this.configurationProperties.getHost());
        redisConfig.setPort(this.configurationProperties.getPort());
        redisConfig.setDatabase(this.configurationProperties.getRedisDatabase());
        redisConfig.setPassword(this.configurationProperties.getPassword());
        redisConfig.setUsername(this.configurationProperties.getUsername());
        return new LettuceConnectionFactory(redisConfig);
    }

    @Bean
    public RedisTemplate<String, String> redisTemplate(){
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<String, String>();
        redisTemplate.setConnectionFactory(redisConnection());
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<String>(String.class));
        return redisTemplate;
    }
    @Bean
    public Logger.Level feignLogger() {
        return  Logger.Level.FULL;
    }

}
