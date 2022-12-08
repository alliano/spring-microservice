package com.account.accountservice.services;

import java.util.HashMap;
import java.util.Optional;

import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.account.accountservice.domain.entities.databases.Account;
import com.account.accountservice.domain.entities.tmpentities.TmpAccount;
import com.account.accountservice.domain.repositories.databasesrepositories.AccountRepository;
import com.account.accountservice.domain.repositories.tmprepositories.TmpAccountRepository;
import com.account.accountservice.dtos.AccountRegisterDto;
import com.account.accountservice.feignclinets.OtpClient;
import com.account.accountservice.services.interfaces.AccountService;

import feign.FeignException;
import lombok.AllArgsConstructor;

@Service @AllArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final TmpAccountRepository tmpAccountRepository;

    private final AccountRepository accountRepository;

    private final OtpClient otpClient;

    private final Environment environment;

    @Override
    public ResponseEntity<?> register(AccountRegisterDto accountRegisterDto) {
        // cek database
        Optional<Account> account = this.accountRepository.findByEmail(accountRegisterDto.getEmail());
        if(account.isPresent()) return ResponseEntity.status(HttpStatus.CONFLICT).body(new HashMap<String, String>().put("message", "username with email "+accountRegisterDto.getEmail()+"has used try another else email"));
        // cek redis
        Optional<TmpAccount> OptionaltmpAccount = this.tmpAccountRepository.findByEmail(accountRegisterDto.getEmail());
        if(OptionaltmpAccount.isPresent()) return ResponseEntity.status(HttpStatus.OK).body(new HashMap<String, String>().put("message", "user has saved"));
        // save
        TmpAccount tmpAccount = new TmpAccount();
        tmpAccount.setEmail(accountRegisterDto.getEmail());
        tmpAccount.setValid(false);
        this.tmpAccountRepository.save(tmpAccount);

        try {
            this.otpClient.requestOtp(accountRegisterDto);
        } catch (FeignException.FeignClientException fexClientException) {
            return ResponseEntity.status(fexClientException.status()).body(fexClientException.contentUTF8());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(new HashMap<String, String>().put("message", "you have been success registered"));
    }

    @Override
    public String logger() {
        return this.environment.getProperty("local.server.host")+":"+this.environment.getProperty("local.server.port");
    }
}
