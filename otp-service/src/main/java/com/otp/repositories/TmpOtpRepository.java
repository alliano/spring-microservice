package com.otp.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.otp.entities.TmpOtp;

public interface TmpOtpRepository extends CrudRepository<TmpOtp, String> {

    public Optional<TmpOtp> findByEmail(String Email);
}
