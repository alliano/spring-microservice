package com.otp.services;

import java.text.DecimalFormat;
import java.util.Optional;
import java.util.Random;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.otp.dtos.RegisterDto;
import com.otp.entities.TmpOtp;
import com.otp.repositories.TmpOtpRepository;
import com.otp.services.interfaces.OtpService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service @Slf4j
@AllArgsConstructor
public class OtpServiceimpl implements OtpService {

    private final Environment environment;

    private final TmpOtpRepository tmpOtpRepository;

    @Override
    public void requestOtp(RegisterDto dto) {

        String email = dto.getEmail();

        Optional<TmpOtp> OptionaltmpOtp = this.tmpOtpRepository.findByEmail(email);

        // cek di redis jika ada maka otp yang lama dihapus

        if(OptionaltmpOtp.isPresent()) this.tmpOtpRepository.delete(OptionaltmpOtp.get());

        // generate otp baru
        String otp = generateOtp();

        log.info("otp : {}", otp);

        // save ke redis database
        TmpOtp tmpOtp = new TmpOtp();

        tmpOtp.setEmail(email);

        tmpOtp.setOtp(otp);

        this.tmpOtpRepository.save(tmpOtp);
    }

    @Override
    public String generateOtp() {
        return new DecimalFormat("0000").format(new Random().nextInt(9999));
    }

    @Override
    public String loggger() {
        String port = this.environment.getProperty("local.server.port");
        String host = this.environment.getProperty("local.server.host");
        return host+":"+port;
    }

}
