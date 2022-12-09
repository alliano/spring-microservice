package com.otp.services;

import java.text.DecimalFormat;
import java.util.Optional;
import java.util.Random;

import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

import com.otp.dtos.EmailDto;
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

    private final RedisTemplate<String, String> redisTemplate;

    private final ChannelTopic channelTopic;

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
        sendOtpcode(email,"kode verifikasi anda : "+otp);
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

    @Override
    public void sendOtpcode(String to, String body) {
        log.info("to {}, body {}", to, body);
        EmailDto emaildto = new EmailDto();
        emaildto.setTo(to);
        emaildto.setBody(body);
        emaildto.setSubject("kode verifikasi anda");
        this.redisTemplate.convertAndSend(this.channelTopic.getTopic(), emaildto);
    }

}
