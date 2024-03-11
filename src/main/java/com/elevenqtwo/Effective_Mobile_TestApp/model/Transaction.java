package com.elevenqtwo.Effective_Mobile_TestApp.model;

import com.elevenqtwo.Effective_Mobile_TestApp.exception.InsufficientBalanceException;
import com.elevenqtwo.Effective_Mobile_TestApp.exception.UserNotFoundException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Setter
    private BigDecimal amount;
    @Setter
    @ManyToOne
    @JoinColumn(name = "source_account_id")
    private BankAccount sourceAccount;
    @Setter
    @ManyToOne
    @JoinColumn(name = "destination_account_id")
    private BankAccount destinationAccount;
    @Setter
    private LocalDateTime timestamp;
}