package com.account.accountservice.controllers.interfaces;

import org.springframework.http.ResponseEntity;

import com.account.accountservice.dtos.AccountRegisterDto;

public interface AccountController {

    public ResponseEntity<?> register(AccountRegisterDto accountRegisterDto);

    public String load();
}
