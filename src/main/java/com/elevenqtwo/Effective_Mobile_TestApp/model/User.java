package com.elevenqtwo.Effective_Mobile_TestApp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.sql.Date;
import java.util.List;

@Getter
@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"login"})
})
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
    @Column(nullable = false)
    public String login; //TODO make unique

    @Setter
    @Column(nullable = false)
    public String password; //TODO hashing

    @Setter
    public Date dateOfBirth;

    @Setter
    @ElementCollection
    @CollectionTable(name = "emails",
            joinColumns = @JoinColumn(name = "user_id"),
            uniqueConstraints =  { @UniqueConstraint(columnNames = {"email"} ) })
    @Column(name = "email", nullable = false)
    public List<String> emails;

    @Setter
    @ElementCollection
    @CollectionTable(name = "phone_numbers",
            joinColumns = @JoinColumn(name = "user_id"),
            uniqueConstraints = { @UniqueConstraint(columnNames = { "phone_number"} ) })
    @Column(name = "phone_number", nullable = false)
    public List<String> phoneNumbers;

    @Setter
    @OneToOne
    @JoinColumn(name = "bank_account_id", nullable = false)
    public BankAccount bankAccount;
}