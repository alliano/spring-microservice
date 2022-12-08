package com.account.accountservice.domain.repositories.tmprepositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.account.accountservice.domain.entities.tmpentities.TmpAccount;

public interface TmpAccountRepository extends CrudRepository<TmpAccount, String> {

    public Optional<TmpAccount> findByEmail(String email);

}
