package com.elevenqtwo.Effective_Mobile_TestApp.repository;

import com.elevenqtwo.Effective_Mobile_TestApp.model.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
    Optional<BankAccount> findById(int id);
}
