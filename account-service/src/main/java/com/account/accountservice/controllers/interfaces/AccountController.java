package com.account.accountservice.controllers.interfaces;

import org.springframework.http.ResponseEntity;

import com.account.accountservice.dtos.AccountRegisterDto;
import com.account.accountservice.dtos.PasswordDto;
import com.account.accountservice.dtos.VerifivationOtpDto;

public interface AccountController {

    public ResponseEntity<?> register(AccountRegisterDto accountRegisterDto);

    public String load();

    public ResponseEntity<?> verification(VerifivationOtpDto verifivationOtp);

    public ResponseEntity<?> createPassword(PasswordDto password);
}
