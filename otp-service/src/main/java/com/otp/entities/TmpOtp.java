package com.otp.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
@RedisHash(value = "otp", timeToLive = 300)
public class TmpOtp {

    @Id
    private String id;

    private String otp;

    @Indexed
    private String email;
}
