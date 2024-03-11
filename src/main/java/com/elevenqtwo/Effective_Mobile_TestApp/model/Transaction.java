package com.elevenqtwo.Effective_Mobile_TestApp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "Transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = "source_account_id")
    private BankAccount sourceAccount;

    @ManyToOne
    @JoinColumn(name = "destination_account_id")
    private BankAccount destinationAccount;

    private LocalDateTime timestamp;
}