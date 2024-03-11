package com.elevenqtwo.Effective_Mobile_TestApp.controller;

import com.elevenqtwo.Effective_Mobile_TestApp.dto.JwtAuthenticationResponseDto;
import com.elevenqtwo.Effective_Mobile_TestApp.dto.SignInRequestDto;
import com.elevenqtwo.Effective_Mobile_TestApp.service.AuthenticationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/users/")
public class UserAuthController {
    private final AuthenticationService authenticationService;

    public UserAuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/sign-in")
    public JwtAuthenticationResponseDto signIn(@RequestBody @Valid SignInRequestDto request) {
         return authenticationService.signIn(request);
    }
}
