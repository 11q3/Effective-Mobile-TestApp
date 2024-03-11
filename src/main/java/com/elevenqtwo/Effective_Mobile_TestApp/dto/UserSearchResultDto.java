package com.elevenqtwo.Effective_Mobile_TestApp.dto;

import com.elevenqtwo.Effective_Mobile_TestApp.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

@Getter
@NoArgsConstructor
public class UserSearchResultDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String middleName;
    private Date dateOfBirth;
    private List<String> emails;
    private List<String> phoneNumbers;

    public UserSearchResultDto(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.middleName = user.getMiddleName();
        this.dateOfBirth = user.getDateOfBirth();
        this.emails = user.getEmails();
        this.phoneNumbers = user.getPhoneNumbers();
    }
}