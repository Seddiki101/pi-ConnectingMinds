package com.projet.usermanagement.controller;


import com.projet.usermanagement.dto.AuthenticationResponse;
import com.projet.usermanagement.dto.EmailRequest;
import com.projet.usermanagement.dto.IdRequest;
import com.projet.usermanagement.dto.RegistrationRequest;
import com.projet.usermanagement.emailer.EmailSender;
import com.projet.usermanagement.entity.User;
import com.projet.usermanagement.service.AuthenticationService;
import com.projet.usermanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.UUID;

@RestController
@RequestMapping("/api/v2/user/auth")
public class AuthenticationController {

    private final AuthenticationService authService;
    @Autowired
    private EmailSender emailSender;
    @Autowired
    private UserService userService;

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

    /*
    @GetMapping(path = "/confirmEmail")
    public String confirm(@RequestParam("token") String token) {

        return authService.confirmToken(token);
    }
    */
    @GetMapping(path = "/confirmEmail")
    public RedirectView confirm(@RequestParam("token") String token) {
        String url = authService.confirmToken(token);
        String url2 = "http://localhost:4200/login" ;
        return new RedirectView(url2);
    }

    /*
    @PostMapping("/loginById")
    public ResponseEntity<AuthenticationResponse> loginById(@RequestBody IdRequest idRequest) {
        return ResponseEntity.ok(authService.authenticateById(idRequest.getId()));
    }
    */



    @PostMapping("/forgot_password")
    public String processForgotPassword(@RequestBody EmailRequest e) {
        User u = userService.getUserbymail(e.getEmail());
        if ( u   != null && u.getEnable() != false ) {

            String token = UUID.randomUUID().toString();

            userService.updateResetPasswordToken(token, e.getEmail());
            String resetPasswordLink = "http://localhost:4200/reset-password?token=" + token;
            emailSender.sendForgetPasswordEmail(e.getEmail(), resetPasswordLink);

            return "We have sent a reset password link to your email. Please check.";
        }
        return "the email doesn t exist " ;
    }


    @PostMapping("/reset_password")
    public String processResetPassword(@RequestParam String token, @RequestParam String password)  {
        // System.out.println("the token that is received in forgot pass " +token);
        User user = userService.getByResetPasswordToken(token);
        if (user != null ) {
            userService.updatePassword(user, password);
            return "your password changed successfully";
        }
        return "unexpected error ";
    }




}
