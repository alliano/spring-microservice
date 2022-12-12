package com.account.accountservice.domain.entities.tmpentities;

import javax.persistence.Id;

import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
@RedisHash(value = "account", timeToLive = 4000)
public class TmpAccount {

    @Id
    private String id;

    @Indexed
    private String email;

    private boolean valid = false;
}
