package com.projet.usermanagement.controller;

import com.projet.usermanagement.dto.EmailRequest;
import com.projet.usermanagement.emailer.EmailSender;
import com.projet.usermanagement.emailer.EmailService;
import com.projet.usermanagement.serviceImp.AuthenticationService;
import com.projet.usermanagement.serviceImp.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class UserUtilsController {
    @Autowired
    private  UserServiceImp userService;
    @Autowired
    private EmailSender emailSender;

//profile, forgot password


    @PostMapping("/forgot_password")
    public String processForgotPassword(@RequestBody EmailRequest e) {
        String token = UUID.randomUUID().toString();
       // System.out.println("test 3");
       // System.out.println( e.getEmail() );
       // System.out.println("end of test 3");

        userService.updateResetPasswordToken(token, e.getEmail() );
        String resetPasswordLink = "http://localhost:4200/changepassword;token=" + token;
        emailSender.sendForgetPasswordEmail( e.getEmail() , resetPasswordLink);

        return "We have sent a reset password link to your email. Please check.";
    }

}
