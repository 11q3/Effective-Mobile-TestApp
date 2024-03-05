package com.elevenqtwo.Effective_Mobile_TestApp.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "bank_accounts")
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;
    @OneToOne(mappedBy = "bankAccount")
    public User user;

    public BigDecimal balance;
}
