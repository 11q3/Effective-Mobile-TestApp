package com.elevenqtwo.Effective_Mobile_TestApp.repository;

import com.elevenqtwo.Effective_Mobile_TestApp.dto.UserSearchResultDto;
import com.elevenqtwo.Effective_Mobile_TestApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByLogin(String login);
    boolean existsByLogin(String login);

    @Query("SELECT COUNT(u) > 0 FROM User u WHERE u IN (SELECT u2 FROM User u2 JOIN u2.emails e WHERE e IN :emails)")
    boolean existsByEmails(List<String> emails);

    @Query("SELECT COUNT(u) > 0 FROM User u WHERE u IN (SELECT u2 FROM User u2 JOIN u2.phoneNumbers p WHERE p IN :phoneNumbers)")
    boolean existsByPhoneNumbers(List<String> phoneNumbers);

    @Query("SELECT u FROM User u " +
            "JOIN FETCH u.emails e WHERE " +
            "(:dateOfBirth IS NULL OR u.dateOfBirth > :dateOfBirth) AND " +
            "(:emails IS NULL OR e IN :emails) AND " +
            "(:phoneNumbers IS NULL OR :phoneNumbers MEMBER OF u.phoneNumbers) AND " +
            "(:fullName IS NULL OR (u.lastName || ' ' || u.firstName || ' ' || u.middleName) LIKE :fullName || '%')")
    List<UserSearchResultDto> searchUsers(@Param("dateOfBirth") Date dateOfBirth,
                                          @Param("emails") List<String> emails,
                                          @Param("phoneNumbers") List<String> phoneNumbers,
                                          @Param("fullName") String fullName);
}