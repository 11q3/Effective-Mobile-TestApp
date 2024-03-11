package com.elevenqtwo.Effective_Mobile_TestApp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Entity
@Table(name = "bank_accounts")
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Setter
    @OneToOne(mappedBy = "bankAccount")
    private User user;

    private BigDecimal balance;

    @Setter
    private BigDecimal initialBalance = BigDecimal.ZERO;

    @PrePersist
    @PreUpdate
    public void checkBalance() {
        if (balance == null || balance.compareTo(BigDecimal.ZERO) < 0) {
            balance = BigDecimal.ZERO;
        }
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
        checkBalance();
    }
}
