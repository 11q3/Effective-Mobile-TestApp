package com.elevenqtwo.Effective_Mobile_TestApp.controller;

import com.elevenqtwo.Effective_Mobile_TestApp.dto.UserUpdateDto;
import com.elevenqtwo.Effective_Mobile_TestApp.exception.*;
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

    @PostMapping("/add-emails")
    public ResponseEntity<Object> addUserEmails(@RequestBody UserUpdateDto userUpdateDto) {
        try {
            userService.addUserEmails(userUpdateDto.id,
                    userUpdateDto.getEmails());
        } catch (UserNotFoundException | UserExistsException |
                 IncorrectUserDataFormatException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok("User emails added successfully!");
    }

    @PostMapping("/add-phone-numbers")
    public ResponseEntity<Object> addUserPhoneNumbers(@RequestBody UserUpdateDto userUpdateDto) {
        try {
            userService.addUserPhoneNumbers(userUpdateDto.id,
                    userUpdateDto.getPhoneNumbers());
        } catch (UserNotFoundException | UserExistsException |
                 IncorrectUserDataFormatException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok("User phone numbers added successfully!");
    }

    @PatchMapping("/patch-emails")
    public ResponseEntity<Object> patchUserEmails(@RequestBody UserUpdateDto userUpdateDto) {
        try {
            userService.patchUserEmails(userUpdateDto.id,
                    userUpdateDto.getEmails(),
                    userUpdateDto.getReplacedEmails());
        }
        catch (UserNotFoundException | UserExistsException | UserDataDoesNotExistException |
               IncorrectUserDataFormatException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok("User emails updated successfully!");
    }

    @PatchMapping("/patch-phone-numbers")
    public ResponseEntity<Object> patchUserPhoneNumbers(@RequestBody UserUpdateDto userUpdateDto) {
        try {
            userService.patchUserPhoneNumbers(userUpdateDto.id,
                    userUpdateDto.getPhoneNumbers(),
                    userUpdateDto.getReplacedPhoneNumbers());
        }
        catch (UserNotFoundException | UserExistsException | UserDataDoesNotExistException |
               IncorrectUserDataFormatException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok("User phone numbers updated successfully!");
    }


    @DeleteMapping("/delete-emails")
    public ResponseEntity<Object> deleteUserEmails(@RequestBody UserUpdateDto userUpdateDto) {
        try {
            userService.deleteUserEmails(userUpdateDto.id,
                    userUpdateDto.getEmails());
        } catch (UserNotFoundException | UserDataDoesNotExistException | UserExistsException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok("User emails deleted successfully!");
    }

    @DeleteMapping("/delete-phone-numbers")
    public ResponseEntity<Object> deleteUserPhoneNumbers(@RequestBody UserUpdateDto userUpdateDto) {
        try {
            userService.deleteUserPhoneNumbers(userUpdateDto.id,
                    userUpdateDto.getPhoneNumbers());
        } catch (UserNotFoundException | UserDataDoesNotExistException | UserExistsException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok("User phone numbers deleted successfully!");
    }
}
