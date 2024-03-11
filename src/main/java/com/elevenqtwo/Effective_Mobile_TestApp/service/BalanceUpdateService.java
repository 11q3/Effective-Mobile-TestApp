package com.elevenqtwo.Effective_Mobile_TestApp.service;

import com.elevenqtwo.Effective_Mobile_TestApp.model.BankAccount;
import com.elevenqtwo.Effective_Mobile_TestApp.model.User;
import com.elevenqtwo.Effective_Mobile_TestApp.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class BalanceUpdateService {

    private static final int FIXED_RATE = 60000; // every minute
    private static final BigDecimal MULTIPLICATION_PERCENT = BigDecimal.valueOf(1.05); // 5%
    private static final BigDecimal LIMIT = BigDecimal.valueOf(2.07); // 207%

    private final UserRepository userRepository;

    public BalanceUpdateService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Scheduled(fixedRate = FIXED_RATE)
    @Transactional
    public void updateBalances() {
        List<User> users = userRepository.findAll();

        for (User user : users) {
            BankAccount bankAccount = user.getBankAccount();
            BigDecimal initialBalance = bankAccount.getInitialBalance();
            BigDecimal newBalance = bankAccount.getBalance().multiply(MULTIPLICATION_PERCENT);

            if (newBalance.compareTo(initialBalance.multiply(LIMIT)) > 0) {
                newBalance = initialBalance.multiply(LIMIT);
            }

            bankAccount.setBalance(newBalance);
            userRepository.save(user);
        }
    }
}