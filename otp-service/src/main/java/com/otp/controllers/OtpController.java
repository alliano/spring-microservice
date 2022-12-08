package com.otp.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.otp.dtos.RegisterDto;
import com.otp.services.interfaces.OtpService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@RestController @Slf4j
public class OtpController {

    private final OtpService otpService;

    @RequestMapping(method = RequestMethod.POST, path = "/request")
    public ResponseEntity<?> createOtp(@RequestBody RegisterDto dto){
        this.otpService.requestOtp(dto);
        log.info("register dto {}", dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @RequestMapping(method = RequestMethod.GET, path = "/load")
    public String tesLoadbalancer(){
        return this.otpService.loggger();
    }
}
