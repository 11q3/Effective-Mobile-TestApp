package com.elevenqtwo.Effective_Mobile_TestApp.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class SignInRequestDto {

    @NotBlank(message = "The username cannot be empty")
    private String username;

    @NotBlank(message = "The password cannot be empty")
    private String password;
}
