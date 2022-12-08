package com.account.accountservice.domain.repositories.databasesrepositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.account.accountservice.domain.entities.databases.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {

    public Optional<Account> findByEmail(String email);
}
