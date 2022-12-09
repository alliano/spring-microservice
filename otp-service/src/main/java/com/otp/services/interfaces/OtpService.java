package com.otp.services.interfaces;

import com.otp.dtos.RegisterDto;

public interface OtpService {

    public void requestOtp(RegisterDto dto);

    public String generateOtp();

    public String loggger();

    public void sendOtpcode(String to, String body);
}
