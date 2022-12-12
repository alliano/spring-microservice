package com.otp.services.interfaces;

import org.springframework.http.ResponseEntity;

import com.otp.dtos.RegisterDto;
import com.otp.dtos.VerifivationDto;

public interface OtpService {

    public void requestOtp(RegisterDto dto);

    public String generateOtp();

    public String loggger();

    public void sendOtpcode(String to, String body);

    public ResponseEntity<?> verifivationOtp(VerifivationDto verifivationDto);
}
