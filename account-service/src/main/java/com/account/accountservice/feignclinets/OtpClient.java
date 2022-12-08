package com.account.accountservice.feignclinets;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.account.accountservice.dtos.AccountRegisterDto;

@FeignClient(name = "otp", url = "http://localhost:8081")
public interface OtpClient {

    @RequestMapping(method = RequestMethod.POST, path = "/request")
    public ResponseEntity<?> requestOtp(@RequestBody AccountRegisterDto dto);
}
