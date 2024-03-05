package com.elevenqtwo.Effective_Mobile_TestApp.controller;

import com.elevenqtwo.Effective_Mobile_TestApp.dto.UserDto;
import com.elevenqtwo.Effective_Mobile_TestApp.model.User;
import com.elevenqtwo.Effective_Mobile_TestApp.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/addUser")
    public void addUser(@RequestBody UserDto userDto) {

       userService.addUser(
                userDto.getFirstName(),
                userDto.getLastName(),
                userDto.getMiddleName(),
                userDto.getLogin(),
                userDto.getPassword(),
                userDto.getBankAccount(),
                userDto.getDateOfBirth(),
                userDto.getPhoneNumbers(),
                userDto.getEmails()
        );
    }
}
