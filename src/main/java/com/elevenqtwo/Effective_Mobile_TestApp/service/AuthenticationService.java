package com.elevenqtwo.Effective_Mobile_TestApp.service;

import com.elevenqtwo.Effective_Mobile_TestApp.dto.JwtAuthenticationResponseDto;
import com.elevenqtwo.Effective_Mobile_TestApp.dto.SignInRequestDto;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserService userService,
                                 JwtService jwtService,
                                 AuthenticationManager
                                 authenticationManager) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }


    public JwtAuthenticationResponseDto signIn(SignInRequestDto request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getLogin(),
                request.getPassword()
        ));

        UserDetails user = userService
                .userDetailsService()
                .loadUserByUsername(request.getLogin());

        String jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponseDto(jwt);
    }
}