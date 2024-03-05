package com.elevenqtwo.Effective_Mobile_TestApp.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;
    @OneToOne
    @JoinColumn(name = "bank_account_id")
    public BankAccount bankAccount;

    public String firstName;
    public String lastName;
    public String middleName;
    public String dateOfBirth;

}
