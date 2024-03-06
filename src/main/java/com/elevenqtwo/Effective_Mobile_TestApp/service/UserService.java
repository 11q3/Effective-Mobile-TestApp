package com.elevenqtwo.Effective_Mobile_TestApp.service;

import com.elevenqtwo.Effective_Mobile_TestApp.model.BankAccount;
import com.elevenqtwo.Effective_Mobile_TestApp.model.User;
import com.elevenqtwo.Effective_Mobile_TestApp.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.sql.Date;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BankAccountService bankAccountService;

    public UserService(UserRepository userRepository, BankAccountService bankAccountService) {
        this.userRepository = userRepository;
        this.bankAccountService = bankAccountService;
    }

    public void addUser(String firstName, String lastName, String middleName, //TODO maybe import there User object
                        String login, String password,
                        Date dateOfBirth, List<String> phoneNumbers, List<String> emails, BankAccount bankAccount) {

        bankAccountService.createBankAccount(bankAccount);

        User user = new User();

        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setMiddleName(middleName);
        user.setLogin(login);  //TODO add proper password hashing here
        user.setPassword(password);
        setEmails(emails, user);
        setPhoneNumbers(phoneNumbers, user);
        user.setDateOfBirth(dateOfBirth);
        user.setBankAccount(bankAccount);

        userRepository.save(user);
    }

    private static void setEmails(List<String> emails, User user) {
        if(user.getEmails() != null) {
            for (String email : emails) {
                user.getEmails().add(email);
            }
        }
        else {
            user.setEmails(emails);
        }
    }

    private static void setPhoneNumbers(List<String> phoneNumbers, User user) {
        if(user.getPhoneNumbers() != null) {
            for (String phoneNumber : phoneNumbers) {
                user.getPhoneNumbers().add(phoneNumber);
            }
        }
        else {
            user.setPhoneNumbers(phoneNumbers);
        }
    }
}