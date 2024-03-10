package com.elevenqtwo.Effective_Mobile_TestApp.service;

import com.elevenqtwo.Effective_Mobile_TestApp.dto.UserSearchResultDto;
import com.elevenqtwo.Effective_Mobile_TestApp.exception.*;
import com.elevenqtwo.Effective_Mobile_TestApp.model.BankAccount;
import com.elevenqtwo.Effective_Mobile_TestApp.model.User;
import com.elevenqtwo.Effective_Mobile_TestApp.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BankAccountService bankAccountService;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, BankAccountService bankAccountService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.bankAccountService = bankAccountService;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void createUser(String firstName, String lastName, String middleName,
                           String login, String password,
                           Date dateOfBirth, List<String> phoneNumbers,
                           List<String> emails, BankAccount bankAccount) throws UserExistsException, IncorrectUserDataFormatException {

        checkForDataFormat(phoneNumbers, emails);
        checkForDataUniqueness(login, phoneNumbers, emails);

        bankAccountService.createBankAccount(bankAccount);

        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setMiddleName(middleName);
        user.setLogin(login);
        user.setPassword(passwordEncoder.encode(password));

        setEmails(emails, user);

        setPhoneNumbers(phoneNumbers, user);

        user.setDateOfBirth(dateOfBirth);
        user.setBankAccount(bankAccount);

        userRepository.save(user);
    }

    @Transactional
    public void addUserPhoneNumbers(Long id, List<String> phoneNumbers) throws UserExistsException, UserNotFoundException, IncorrectUserDataFormatException {
        User user = userRepository.findById(id).orElseThrow(() ->
                new UserNotFoundException("User not found with id: " + id));

        checkForDataFormat(phoneNumbers, null);
        checkForDataUniqueness(null, phoneNumbers, null);

        setPhoneNumbers(phoneNumbers, user);

        userRepository.save(user);
    }

    @Transactional
    public void addUserEmails(Long id, List<String> emails) throws UserExistsException, UserNotFoundException, IncorrectUserDataFormatException { //TODO maybe surround with try/catch
        User user = userRepository.findById(id).orElseThrow(() ->
                new UserNotFoundException("User not found with id: " + id));

        checkForDataFormat(null, emails);
        checkForDataUniqueness(null, null, emails);

        setEmails(emails, user);

        userRepository.save(user);
    }

    @Transactional
    public void patchUserEmails(Long id, List<String> emails, List<String> replacedEmails)  //TODO maybe surround with try/catch
            throws UserNotFoundException, UserExistsException, UserDataDoesNotExistException, IncorrectUserDataFormatException {
        User user = userRepository.findById(id).orElseThrow(() ->
                new UserNotFoundException("User not found with id: " + id));

        checkForDataFormat(null, emails);
        checkForDataPresence(emails, user.getEmails());
        checkForDataUniqueness(null, null, emails);

        replaceEmails(emails, replacedEmails, user);

        userRepository.save(user);
    }

    @Transactional
    public void patchUserPhoneNumbers(Long id, List<String> phoneNumbers, List<String> replacedPhoneNumbers)  //TODO maybe surround with try/catch
            throws UserNotFoundException, UserExistsException, UserDataDoesNotExistException, IncorrectUserDataFormatException {
        User user = userRepository.findById(id).orElseThrow(() ->
                new UserNotFoundException("User not found with id: " + id));

        checkForDataFormat(phoneNumbers, null);
        checkForDataPresence(phoneNumbers, user.getPhoneNumbers());
        checkForDataUniqueness(null, phoneNumbers, null); //TODO add check if replacedPhoneNumbers are exist

        replacePhoneNumbers(phoneNumbers, replacedPhoneNumbers, user);

        userRepository.save(user);
    }

    @Transactional
    public void deleteUserPhoneNumbers(Long id, List<String> phoneNumbers) throws UserNotFoundException, UserDataDoesNotExistException, UserExistsException { //TODO maybe surround with try/catch
        User user = userRepository.findById(id).orElseThrow(() ->
                new UserNotFoundException("User not found with id: " + id));

        checkForDataPresence(phoneNumbers, user.getPhoneNumbers());

        if (phoneNumbers.size() >= user.getPhoneNumbers().size())
            throw new NoUserFieldsRemainingException("phone_number");

        for (String phoneNumber: phoneNumbers) {
            user.getPhoneNumbers().remove(phoneNumber);
        }

        userRepository.save(user);
    }

    @Transactional
    public void deleteUserEmails(Long id, List<String> emails) throws UserNotFoundException, UserDataDoesNotExistException, UserExistsException { //TODO maybe surround with try/catch
        User user = userRepository.findById(id).orElseThrow(() ->
                new UserNotFoundException("User not found with id: " + id));

        checkForDataPresence(emails, user.getEmails());

        if (emails.size() >= user.getEmails().size())
            throw new NoUserFieldsRemainingException("email");

        for (String email: emails) {
            user.getEmails().remove(email);
        }

        userRepository.save(user);
    }

    @Transactional
    public List<UserSearchResultDto> searchUsers(Date dateOfBirth, List<String> emails, List<String> phoneNumbers, String fullName) {
        return userRepository.searchUsers(dateOfBirth, emails, phoneNumbers, fullName);
    }
    private void checkForDataPresence(List<String> providedFields, List<String> userFields) throws UserDataDoesNotExistException { //TODO maybe surround with try/catch
        if (providedFields != null && userFields != null) {
            Set<String> intersection = new HashSet<>(providedFields);
            intersection.retainAll(userFields);

            if (intersection.size() != providedFields.size()) {
                throw new UserDataDoesNotExistException("Fields provided for deletion do not exist for the user");
            }
        }
    }


    private void checkForDataUniqueness(String login, List<String> phoneNumbers, List<String> emails) throws UserExistsException { //TODO maybe surround with try/catch
        if (userRepository.existsByLogin(login)) {
            throw new UserExistsException("A user with this login already exists");
        }
        if (emails != null) {
            if(userRepository.existsByEmails(emails)) {
                throw new UserExistsException("A user with this email already exists");
            }
        }

        if (phoneNumbers != null) {
            if (userRepository.existsByPhoneNumbers(phoneNumbers)) {
                throw new UserExistsException("A user with this phone number already exists"); //TODO maybe change exception body
            }
        }
    }

    private void checkForDataFormat(List<String> phoneNumbers, List<String> emails) throws IncorrectUserDataFormatException { //TODO maybe surround with try/catch
        if (emails != null) {
            for (String email : emails) {
                String emailRegex = "[_A-Za-z0-9-]+@[A-Za-z0-9-]+(.[A-Za-z0-9-]+)";

                if (!email.matches(emailRegex)) {
                    throw new IncorrectUserDataFormatException("An email is not in a valid format");
                }
            }
        }

        if (phoneNumbers != null) {
            for (String phoneNumber : phoneNumbers) {
                String phoneNumberRegex = "^\\+[0-9]{1,3}-?[0-9]{1,4}-?[0-9]{1,4}-?[0-9]{1,4}-?[0-9]{1,4}$";

                if (!phoneNumber.matches(phoneNumberRegex)) {
                    throw new IncorrectUserDataFormatException("A phone number is not in a valid format"); //E.164 numbering plan.
                }
            }
        }
    }

    private void replaceEmails(List<String> replacingEmails, List<String> replacedEmails, User user) { //TODO add javadoc maybe
        List<String> userEmails = user.getEmails();

        for(String replacedEmail : replacedEmails)
            userEmails.remove(replacedEmail);
        userEmails.addAll(replacingEmails);

        user.setEmails(userEmails);
    }

    private void replacePhoneNumbers(List<String> replacingPhoneNumbers, List<String> replacedPhoneNumbers, User user) { //TODO add javadoc maybe
        List<String> userEmails = user.getEmails();

        for(String replacedPhoneNumber : replacedPhoneNumbers)
            userEmails.remove(replacedPhoneNumber);
        userEmails.addAll(replacingPhoneNumbers);

        user.setEmails(userEmails);
    }


    private static void setEmails(List<String> emails, User user) {
        if(user.getEmails() == null) {
            user.setEmails(emails);
        } else {
            for (String email : emails) {
                user.getEmails().add(email);
            }
        }
    }

    private static void setPhoneNumbers(List<String> phoneNumbers, User user) {
        if(user.getPhoneNumbers() == null) {
            user.setPhoneNumbers(phoneNumbers);
        } else {
            for (String phoneNumber : phoneNumbers) {
                user.getPhoneNumbers().add(phoneNumber);
            }
        }
    }

    public User getByUsername(String username) {
        try {
            return userRepository.findByLogin(username)
                    .orElseThrow(() -> new UserNotFoundException("User is not found"));
        } catch (UserNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public UserDetailsService userDetailsService() {
        return this::getByUsername;
    }
}