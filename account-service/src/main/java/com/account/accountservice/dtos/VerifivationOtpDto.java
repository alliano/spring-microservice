package com.account.accountservice.dtos;

import lombok.Data;

@Data
public class VerifivationOtpDto {

    private String email;

    private String otp;
}
