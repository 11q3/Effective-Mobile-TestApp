package com.elevenqtwo.Effective_Mobile_TestApp.service;

import com.elevenqtwo.Effective_Mobile_TestApp.exception.InsufficientBalanceException;
import com.elevenqtwo.Effective_Mobile_TestApp.exception.UserNotFoundException;
import com.elevenqtwo.Effective_Mobile_TestApp.model.BankAccount;
import com.elevenqtwo.Effective_Mobile_TestApp.model.Transaction;
import com.elevenqtwo.Effective_Mobile_TestApp.model.User;
import com.elevenqtwo.Effective_Mobile_TestApp.repository.TransactionRepository;
import com.elevenqtwo.Effective_Mobile_TestApp.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class TransactionService {
    private final UserRepository userRepository;

    private final TransactionRepository transactionRepository;

    public TransactionService(UserRepository userRepository, TransactionRepository transactionRepository) {
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
    }

    @Transactional
    public void transfer(Long sourceUserId, Long destinationUserId, BigDecimal amount) throws UserNotFoundException, InsufficientBalanceException {
        User sourceUser = userRepository.findById(sourceUserId).orElseThrow(() ->
                new UserNotFoundException("Source user not found"));
        User destinationUser = userRepository.findById(destinationUserId).orElseThrow(() ->
                new UserNotFoundException("Destination user not found"));

        BankAccount sourceAccount = sourceUser.getBankAccount();
        BankAccount destinationAccount = destinationUser.getBankAccount();

        if (sourceAccount.getBalance().compareTo(amount) < 0) {
            throw new InsufficientBalanceException("Insufficient balance");
        }

        sourceAccount.setBalance(sourceAccount.getBalance().subtract(amount));
        destinationAccount.setBalance(destinationAccount.getBalance().add(amount));

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setSourceAccount(sourceAccount);
        transaction.setDestinationAccount(destinationAccount);
        transaction.setTimestamp(LocalDateTime.now());

        transactionRepository.save(transaction);
    }
}
