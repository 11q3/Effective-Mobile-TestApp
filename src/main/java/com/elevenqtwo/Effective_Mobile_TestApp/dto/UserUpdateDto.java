package com.elevenqtwo.Effective_Mobile_TestApp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class UserUpdateDto {
    public Long id;
    private List<String> emails;
    @JsonProperty("phone_numbers")
    private List<String> phoneNumbers;
}
