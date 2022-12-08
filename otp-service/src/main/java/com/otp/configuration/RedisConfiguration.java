package com.otp.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import lombok.AllArgsConstructor;

@Configuration
@EnableRedisRepositories @AllArgsConstructor
public class RedisConfiguration {

    private final ApplicationProperties applicationProperties;

    @Bean
    public LettuceConnectionFactory redisConnection(){
        RedisStandaloneConfiguration redisConf = new RedisStandaloneConfiguration();
        redisConf.setDatabase(0);
        redisConf.setHostName(this.applicationProperties.getHost());
        redisConf.setPassword(this.applicationProperties.getPassword());
        redisConf.setUsername(this.applicationProperties.getUsername());
        return new LettuceConnectionFactory(redisConf);
    }

    @Bean
    public RedisTemplate<String, String> redisTemplate(){
        RedisTemplate<String, String> template = new RedisTemplate<String, String>();
        template.setConnectionFactory(redisConnection());
        template.setKeySerializer(new Jackson2JsonRedisSerializer<String>(String.class));
        return template;
    }

}
