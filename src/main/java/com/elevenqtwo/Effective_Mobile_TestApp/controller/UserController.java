package com.elevenqtwo.Effective_Mobile_TestApp.controller;

import com.elevenqtwo.Effective_Mobile_TestApp.dto.UserDto;
import com.elevenqtwo.Effective_Mobile_TestApp.exception.UserExistsException;
import com.elevenqtwo.Effective_Mobile_TestApp.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/addUser")
    public ResponseEntity<Object> addUser(@RequestBody UserDto userDto) {
        try {
            userService.addUser(
                    userDto.getFirstName(),
                    userDto.getLastName(),
                    userDto.getMiddleName(),
                    userDto.getLogin(),
                    userDto.getPassword(),
                    userDto.getDateOfBirth(),
                    userDto.getPhoneNumbers(),
                    userDto.getEmails(),
                    userDto.getBankAccount()
            );
        }
        catch (UserExistsException e) {
            throw new RuntimeException(e);
        }

        return ResponseEntity.ok("User added successfully!");
    }
}
