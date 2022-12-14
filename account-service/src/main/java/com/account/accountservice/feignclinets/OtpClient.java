package com.account.accountservice.feignclinets;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.account.accountservice.dtos.AccountRegisterDto;
import com.account.accountservice.dtos.VerifivationOtpDto;

@FeignClient(name = "otp")
public interface OtpClient {

    @RequestMapping(method = RequestMethod.POST, path = "/request")
    public ResponseEntity<?> requestOtp(@RequestBody AccountRegisterDto dto);

    @RequestMapping(method = RequestMethod.GET, path = "/load")
    public String tesLoadbalancer();

    @RequestMapping(method = RequestMethod.POST, path = "/verification")
    public ResponseEntity<?> verificationOtp(@RequestBody VerifivationOtpDto string);
}
