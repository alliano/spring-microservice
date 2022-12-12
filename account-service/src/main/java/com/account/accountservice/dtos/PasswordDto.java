package com.account.accountservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class PasswordDto {

    private String email;

    private String password;
}
