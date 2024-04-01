package com.projet.usermanagement.controller;


import com.projet.usermanagement.dto.AuthenticationResponse;
import com.projet.usermanagement.dto.RegistrationRequest;
import com.projet.usermanagement.entity.User;
import com.projet.usermanagement.serviceImp.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthenticationController {

    private final AuthenticationService authService;

    public AuthenticationController(AuthenticationService authService) {
        this.authService = authService;
    }


    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register( @RequestBody RegistrationRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody User request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }

    @GetMapping(path = "/confirmEmail")
    public String confirm(@RequestParam("token") String token) {

        System.out.println("to ken " +token);
        return authService.confirmToken(token);
    }



}
