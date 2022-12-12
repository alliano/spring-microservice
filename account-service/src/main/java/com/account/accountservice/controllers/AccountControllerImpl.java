package com.account.accountservice.controllers;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.account.accountservice.controllers.interfaces.AccountController;
import com.account.accountservice.dtos.AccountRegisterDto;
import com.account.accountservice.dtos.PasswordDto;
import com.account.accountservice.dtos.VerifivationOtpDto;
import com.account.accountservice.services.interfaces.AccountService;

import lombok.AllArgsConstructor;

@RestController @AllArgsConstructor
public class AccountControllerImpl implements AccountController{

    private final AccountService accountService;

    @Override
    @RequestMapping(method = RequestMethod.POST, path = "/register")
    public ResponseEntity<?> register(@RequestBody AccountRegisterDto accountRegisterDto) {
        return this.accountService.register(accountRegisterDto);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/load")
    @Override
    public String load() {
        return this.accountService.logger();
    }

    @RequestMapping(method = RequestMethod.POST, path = "/verification")
    @Override
    public ResponseEntity<?> verification(@RequestBody VerifivationOtpDto verifivationDto) {
        return this.accountService.verifivation(verifivationDto);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/password")
    @Override
    public ResponseEntity<?> createPassword(@RequestBody PasswordDto password) {
        return this.accountService.createPassword(password);
    }

}
