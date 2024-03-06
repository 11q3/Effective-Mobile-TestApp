package com.elevenqtwo.Effective_Mobile_TestApp.service;

import com.elevenqtwo.Effective_Mobile_TestApp.exception.UserExistsException;
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

    public void addUser(String firstName, String lastName, String middleName,
                        String login, String password,
                        Date dateOfBirth, List<String> phoneNumbers,
                        List<String> emails, BankAccount bankAccount)  throws UserExistsException {
        checkForDataUniqueness(login, phoneNumbers, emails);

        bankAccountService.createBankAccount(bankAccount);

        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setMiddleName(middleName);
        user.setLogin(login);
        user.setPassword(password);

        setEmails(emails, user);

        setPhoneNumbers(phoneNumbers, user);

        user.setDateOfBirth(dateOfBirth);
        user.setBankAccount(bankAccount);

        userRepository.save(user);
    }

    private void checkForDataUniqueness(String login, List<String> phoneNumbers, List<String> emails) throws UserExistsException {
        if (userRepository.findByLogin(login).isPresent()) {
            throw new UserExistsException("A user with this login already exists");
        }
        if (userRepository.findByEmails(emails, emails.size()).isPresent()) {
            throw new UserExistsException("A user with this email already exists");
        }

        if (userRepository.findByPhoneNumbers(phoneNumbers, phoneNumbers.size()).isPresent()) {
            throw new UserExistsException("A user with this phone number already exists");
        }
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