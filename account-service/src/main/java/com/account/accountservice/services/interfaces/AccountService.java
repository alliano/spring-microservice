package com.account.accountservice.services.interfaces;


import org.springframework.http.ResponseEntity;

import com.account.accountservice.dtos.AccountRegisterDto;

public interface AccountService {

    public ResponseEntity<?> register(AccountRegisterDto accountRegisterDto);

    public String logger();
}
