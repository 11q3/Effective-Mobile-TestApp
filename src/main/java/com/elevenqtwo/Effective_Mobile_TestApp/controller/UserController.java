package com.elevenqtwo.Effective_Mobile_TestApp.controller;

import com.elevenqtwo.Effective_Mobile_TestApp.dto.UserDto;
import com.elevenqtwo.Effective_Mobile_TestApp.dto.UserUpdateDto;
import com.elevenqtwo.Effective_Mobile_TestApp.exception.UserDataDoesNotExistException;
import com.elevenqtwo.Effective_Mobile_TestApp.exception.UserExistsException;
import com.elevenqtwo.Effective_Mobile_TestApp.exception.UserNotFoundException;
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

    @PostMapping("/addUserPhoneNumbers")
    public ResponseEntity<Object> addUserPhoneNumbers(@RequestBody UserUpdateDto userUpdateDto) {
        try {
            userService.addUserPhoneNumbers(
                    userUpdateDto.id,
                    userUpdateDto.getPhoneNumbers()
            );
        } catch (UserExistsException | UserNotFoundException e) {
            throw new RuntimeException(e);
        }

        return ResponseEntity.ok("User updated successfully!");
    }

    @PostMapping("/addUserEmails")
    public ResponseEntity<Object> addUserEmails(@RequestBody UserUpdateDto userUpdateDto) {
        try { //TODO extract this logic deeper. maybe.
            userService.addUserEmails(
                    userUpdateDto.id,
                    userUpdateDto.getEmails()
            );
        } catch (UserExistsException | UserNotFoundException e) {
            throw new RuntimeException(e);
        }

        return ResponseEntity.ok("User emails added successfully!");
    }

    @PatchMapping("/patchUserEmails")
    public ResponseEntity<Object> patchUserEmails(@RequestBody UserUpdateDto userUpdateDto) {
        try {
            userService.patchUserEmails(userUpdateDto.id,
                    userUpdateDto.getEmails(),
                    userUpdateDto.getReplacedEmails());
        }
         catch (UserNotFoundException | UserExistsException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok("User emails updated successfully!");
    }

    @PatchMapping("/patchUserPhoneNumbers")
    public ResponseEntity<Object> patchUserPhoneNumbers(@RequestBody UserUpdateDto userUpdateDto) {
        try {
            userService.patchUserPhoneNumbers(userUpdateDto.id,
                    userUpdateDto.getPhoneNumbers(),
                    userUpdateDto.getReplacedPhoneNumbers());
        }
        catch (UserNotFoundException | UserExistsException | UserDataDoesNotExistException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok("User phone numbers updated successfully!");
    }
}
