package com.elevenqtwo.Effective_Mobile_TestApp.repository;

import com.elevenqtwo.Effective_Mobile_TestApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByLogin(String login);
    boolean existsByEmails(List<String> email);
    boolean existsByPhoneNumbers(List<String> phoneNumber);

}
