package com.elevenqtwo.Effective_Mobile_TestApp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    @Getter
    @Setter
    @OneToOne
    @JoinColumn(name = "bank_account_id", unique = true)
    public BankAccount bankAccount;

    @Getter
    @Setter
    public String firstName;

    @Getter
    @Setter
    public String lastName;

    @Getter
    @Setter
    public String middleName;

    @Getter
    @Setter
    public String dateOfBirth; //TODO maybe another datatype here

    @Getter
    @Setter
    @ElementCollection
    @CollectionTable(name = "emails",
            joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "email")
    public List<String> emails; //TODO make unique

    @Getter
    @Setter
    @ElementCollection
    @CollectionTable(name = "phone_numbers",
            joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "phone_number")
    public List<String> phoneNumbers; //TODO make unique


    @Getter
    @Setter
    public String login; //TODO make unique
    @Getter
    @Setter
    public String password; //TODO hashing
}
