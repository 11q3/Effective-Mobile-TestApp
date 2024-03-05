package com.elevenqtwo.Effective_Mobile_TestApp.service;

import com.elevenqtwo.Effective_Mobile_TestApp.model.BankAccount;
import com.elevenqtwo.Effective_Mobile_TestApp.model.User;
import com.elevenqtwo.Effective_Mobile_TestApp.repository.UserRepository;
import org.springframework.stereotype.Service;
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
                        BankAccount bankAccount, String dateOfBirth, List<String> emails, List<String> phoneNumbers) {

        bankAccountService.createBankAccount(bankAccount);



        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setMiddleName(middleName);
        user.setDateOfBirth(dateOfBirth);

        user.setBankAccount(bankAccount);

        user.setLogin(login);

        if(user.getEmails() != null) {
            for (String email : emails) {
                user.getEmails().add(email);
            }
        }
        else {
            user.setEmails(emails);
        }

        if(user.getPhoneNumbers() != null) {
            for (String phoneNumber : phoneNumbers) {
                user.getPhoneNumbers().add(phoneNumber);
            }
        }
        else {
            user.setPhoneNumbers(phoneNumbers);
        }

        //TODO add proper password hashing here
        user.setPassword(password);

        userRepository.save(user);
    }
}