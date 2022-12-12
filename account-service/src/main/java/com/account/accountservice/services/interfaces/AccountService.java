package com.account.accountservice.services.interfaces;


import org.springframework.http.ResponseEntity;

import com.account.accountservice.dtos.AccountRegisterDto;
import com.account.accountservice.dtos.PasswordDto;
import com.account.accountservice.dtos.VerifivationOtpDto;

public interface AccountService {

    public ResponseEntity<?> register(AccountRegisterDto accountRegisterDto);

    public String logger();

    public ResponseEntity<?> verifivation(VerifivationOtpDto verifivationOtp);

    public ResponseEntity<?> createPassword(PasswordDto passwordDto);
}
