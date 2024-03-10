package com.elevenqtwo.Effective_Mobile_TestApp.controller;
import com.elevenqtwo.Effective_Mobile_TestApp.dto.UserDto;
import com.elevenqtwo.Effective_Mobile_TestApp.dto.UserSearchResultDto;
import com.elevenqtwo.Effective_Mobile_TestApp.dto.UserUpdateDto;
import com.elevenqtwo.Effective_Mobile_TestApp.exception.IncorrectUserDataFormatException;
import com.elevenqtwo.Effective_Mobile_TestApp.exception.UserDataDoesNotExistException;
import com.elevenqtwo.Effective_Mobile_TestApp.exception.UserExistsException;
import com.elevenqtwo.Effective_Mobile_TestApp.exception.UserNotFoundException;
import com.elevenqtwo.Effective_Mobile_TestApp.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/createUser")
    public ResponseEntity<Object> createUser(@RequestBody UserDto userDto) {
        try {
            userService.createUser(
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
        catch (UserExistsException | IncorrectUserDataFormatException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully!");
    }

    @PostMapping("/addUserEmails")
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

    @PostMapping("/addUserPhoneNumbers")
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

    @DeleteMapping("/deleteUserEmails")
    public ResponseEntity<Object> deleteUserEmails(@RequestBody UserUpdateDto userUpdateDto) {
        try {
            userService.deleteUserEmails(userUpdateDto.id,
                    userUpdateDto.getEmails());
        } catch (UserNotFoundException | UserDataDoesNotExistException | UserExistsException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok("User emails deleted successfully!");
    }

    @DeleteMapping("/deleteUserPhoneNumbers")
    public ResponseEntity<Object> deleteUserPhoneNumbers(@RequestBody UserUpdateDto userUpdateDto) {
        try {
            userService.deleteUserPhoneNumbers(userUpdateDto.id,
                    userUpdateDto.getPhoneNumbers());
        } catch (UserNotFoundException | UserDataDoesNotExistException | UserExistsException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok("User phone numbers deleted successfully!");
    }


    @GetMapping("/search")
    public ResponseEntity<CollectionModel<UserSearchResultDto>> searchUsers(
            @RequestParam(required = false) Date dateOfBirth,
            @RequestParam(required = false) List<String> emails,
            @RequestParam(required = false) List<String> phoneNumbers,
            @RequestParam(required = false) String fullName,
            @RequestParam(defaultValue = "id,asc") String[] sort,
            Pageable pageable) {

        Page<UserSearchResultDto> users = userService.searchUsers(dateOfBirth, emails, phoneNumbers, fullName, pageable);

        CollectionModel<UserSearchResultDto> pagedModel = PagedModel.of(users);

        pagedModel.add(Link.of("/search?page=" + pageable.getPageNumber() +
                "&size=" + pageable.getPageSize() + "&sort=" + sort[0] + "," + sort[1], "self").withSelfRel());
        pagedModel.add(Link.of("/search?page=0&size=" + pageable.getPageSize() +
                "&sort=" + sort[0] + "," + sort[1], "first").withRel("first"));
        pagedModel.add(Link.of("/search?page=" + (users.getTotalPages() - 1) +
                "&size=" + pageable.getPageSize() + "&sort=" + sort[0] + "," + sort[1], "self").withRel("last"));
        pagedModel.add(Link.of("/search?page=" + (pageable.getPageNumber() + 1) +
                "&size=" + pageable.getPageSize() + "&sort=" + sort[0] + "," + sort[1], "self").withRel("next"));
        pagedModel.add(Link.of("/search?page=" + (pageable.getPageNumber() - 1) +
                "&size=" + pageable.getPageSize() + "&sort=" + sort[0] + "," + sort[1], "self").withRel("prev"));

        return ResponseEntity.ok(pagedModel);
    }
}
