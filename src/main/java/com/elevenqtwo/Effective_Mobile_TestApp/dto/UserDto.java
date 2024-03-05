package com.elevenqtwo.Effective_Mobile_TestApp.dto;


import com.elevenqtwo.Effective_Mobile_TestApp.model.BankAccount;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class UserDto {
    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("middle_name")
    private String middleName;

    private String login;

    private String password;

    @JsonProperty("start_sum")
    private BigDecimal startSum;

    @JsonProperty("date_of_birth")
    private String dateOfBirth;

    private List<String> emails;

    @JsonProperty("phone_numbers")
    private List<String> phoneNumbers;

    @JsonProperty("bank_account")
    private BankAccount bankAccount;
}