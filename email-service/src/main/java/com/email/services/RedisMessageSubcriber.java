package com.email.services;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import com.email.dtos.EmailDto;
import com.email.services.interfaces.EmailService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;

@Service @AllArgsConstructor
public class RedisMessageSubcriber implements MessageListener {

    private final EmailService emailService;

    private final ObjectMapper objectMapper;

    @Override
    public void onMessage(Message message, @Nullable byte[] pattern) {
        String msg = message.toString();
        try {
            EmailDto emailDto = this.objectMapper.readValue(msg, EmailDto.class);
            this.emailService.sendEmail(emailDto.getTo(),emailDto.getSubject(),emailDto.getBody());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}
