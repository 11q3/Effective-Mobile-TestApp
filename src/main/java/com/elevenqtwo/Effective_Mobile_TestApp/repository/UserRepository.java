package com.elevenqtwo.Effective_Mobile_TestApp.repository;

import com.elevenqtwo.Effective_Mobile_TestApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLogin(String login);

    @Query("SELECT u FROM User u JOIN u.emails e WHERE e IN :emails GROUP BY u HAVING COUNT(u) = :emailCount")
    Optional<User> findByEmails(@Param("emails") List<String> emails, @Param("emailCount") int emailCount);

    @Query("SELECT u FROM User u JOIN u.phoneNumbers p WHERE p IN :phoneNumbers GROUP BY u HAVING COUNT(u) = :phoneCount")
    Optional<User> findByPhoneNumbers(@Param("phoneNumbers") List<String> phoneNumbers, @Param("phoneCount") int phoneCount);

}
