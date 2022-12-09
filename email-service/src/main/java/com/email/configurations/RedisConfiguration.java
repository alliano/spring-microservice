package com.email.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import com.email.services.RedisMessageSubcriber;

import lombok.AllArgsConstructor;

@Configuration @AllArgsConstructor
@EnableRedisRepositories
public class RedisConfiguration {

    private final ApplicationProperties applicationProperties;

    private final RedisMessageSubcriber redisMessageSubcriber;

    @Bean
    public LettuceConnectionFactory redisConnectionFactory(){
        RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration();
        redisConfig.setHostName(this.applicationProperties.getHost());
        redisConfig.setPort(this.applicationProperties.getPort());
        redisConfig.setDatabase(this.applicationProperties.getRedisDatabase());
        redisConfig.setUsername(this.applicationProperties.getUsername());
        redisConfig.setPassword(this.applicationProperties.getPassword());
        return new LettuceConnectionFactory(redisConfig);
    }

    @Bean
    public ChannelTopic redisChanelTopc(){
        return new ChannelTopic(this.applicationProperties.getChanelName());
    }

    @Bean
    public MessageListener redisMessageListerner() {
        return new MessageListenerAdapter(this.redisMessageSubcriber);
    }

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(){
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory());
        container.addMessageListener(redisMessageListerner(), redisChanelTopc());
        return container;
    }
}
