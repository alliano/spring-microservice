package com.account.accountservice.services;

import java.util.HashMap;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.account.accountservice.domain.entities.databases.Account;
import com.account.accountservice.domain.entities.tmpentities.TmpAccount;
import com.account.accountservice.domain.repositories.databasesrepositories.AccountRepository;
import com.account.accountservice.domain.repositories.tmprepositories.TmpAccountRepository;
import com.account.accountservice.dtos.AccountRegisterDto;
import com.account.accountservice.dtos.PasswordDto;
import com.account.accountservice.dtos.VerifivationOtpDto;
import com.account.accountservice.feignclinets.OtpClient;
import com.account.accountservice.services.interfaces.AccountService;

import feign.FeignException;
import feign.FeignException.FeignClientException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service @AllArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final TmpAccountRepository tmpAccountRepository;

    private final AccountRepository accountRepository;

    private final OtpClient otpClient;

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
        return ResponseEntity.status(HttpStatus.CREATED).body(new HashMap<String, String>().put("message", "success registered"));
    }

    @Override
    public String logger() {
        return this.otpClient.tesLoadbalancer();
    }

    @Override
    public ResponseEntity<?> verifivation(VerifivationOtpDto verifivationOtp) {
        HashMap<String, String> success  = new HashMap<String, String>();
        HashMap<String, String> faill  = new HashMap<String, String>();
        HashMap<String, String> otpFaill  = new HashMap<String, String>();
        success.put("success", "verification success");
        faill.put("failed", "email doesn't valid");
        otpFaill.put("failed", "otp doesn't valid");
        // cek di redis email nya valid apa nga
        Optional<TmpAccount> tmpAccount = this.tmpAccountRepository.findByEmail(verifivationOtp.getEmail());
        if(tmpAccount.isEmpty()) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(faill);
        //cek apakah otp nya valid
        try {
            this.otpClient.verificationOtp(verifivationOtp);
        } catch (FeignClientException FEX) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(otpFaill);
        }
        //simpan
        tmpAccount.get().setValid(true);
        this.tmpAccountRepository.save(tmpAccount.get());
        log.info("verification success");
        return ResponseEntity.status(HttpStatus.OK).body(success);
    }

    @Override
    public ResponseEntity<?> createPassword(PasswordDto passwordDto) {
        HashMap<String, String> emailFaill = new HashMap<>();
        HashMap<String, String> verificationFaill = new HashMap<>();
        HashMap<String, String> success = new HashMap<>();
        // cek apakah email udah ada di redis database apa belum
        Optional<TmpAccount> tmpAccount = this.tmpAccountRepository.findByEmail(passwordDto.getEmail());
        if(tmpAccount.isEmpty()) {
            emailFaill.put("mesage", "email doesn't valid");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(emailFaill);
        }
        // cek apakah sudah di verifikasi
        if(!tmpAccount.get().isValid()) {
            verificationFaill.put("message", "accoun yet to do verification");
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(verificationFaill);
        }
        // simpan ke database
        Account account = new Account();
        account.setEmail(tmpAccount.get().getEmail());
        account.setPassword(passwordDto.getPassword());
        // delete data registrasi yang ada di database redis
        this.tmpAccountRepository.deleteById(tmpAccount.get().getId());
        success.put("message", "success registered");
        return ResponseEntity.status(HttpStatus.OK).body(success);
    }


}
