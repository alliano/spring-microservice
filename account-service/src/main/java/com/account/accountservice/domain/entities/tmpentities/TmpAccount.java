package com.account.accountservice.domain.entities.tmpentities;

import javax.persistence.Id;

import org.springframework.data.redis.core.RedisHash;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
@RedisHash(value = "account", timeToLive = 1000)
public class TmpAccount {

    @Id
    private String id;

    private String email;

    private boolean valid = false;
}
