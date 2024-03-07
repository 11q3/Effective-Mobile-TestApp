package com.elevenqtwo.Effective_Mobile_TestApp.repository;

import com.elevenqtwo.Effective_Mobile_TestApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByLogin(String login);

    @Query("SELECT COUNT(u) > 0 FROM User u WHERE u IN (SELECT u2 FROM User u2 JOIN u2.emails e WHERE e IN :emails)")
    boolean existsByEmails(List<String> emails);

    @Query("SELECT COUNT(u) > 0 FROM User u WHERE u IN (SELECT u2 FROM User u2 JOIN u2.phoneNumbers p WHERE p IN :phoneNumbers)")
    boolean existsByPhoneNumbers(List<String> phoneNumbers);
}
