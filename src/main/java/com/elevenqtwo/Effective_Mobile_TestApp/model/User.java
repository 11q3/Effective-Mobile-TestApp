package com.elevenqtwo.Effective_Mobile_TestApp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;   
import java.sql.Date;
import java.util.List;

@Getter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    @Setter
    public String firstName;

    @Setter
    public String lastName;

    @Setter
    public String middleName;

    @Setter
    public String login; //TODO make unique

    @Setter
    public String password; //TODO hashing

    @Setter
    public Date dateOfBirth;

    @Setter
    @ElementCollection
    @CollectionTable(name = "emails",
            joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "email")
    public List<String> emails; //TODO make unique

    @Setter
    @ElementCollection
    @CollectionTable(name = "phone_numbers",
            joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "phone_number")
    public List<String> phoneNumbers; //TODO make unique

    @Setter
    @OneToOne
    @JoinColumn(name = "bank_account_id", unique = true)
    public BankAccount bankAccount;
}