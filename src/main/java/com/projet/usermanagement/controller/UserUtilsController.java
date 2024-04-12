package com.projet.usermanagement.controller;

import com.projet.usermanagement.dto.EmailRequest;
import com.projet.usermanagement.emailer.EmailSender;
import com.projet.usermanagement.emailer.EmailService;
import com.projet.usermanagement.entity.User;
import com.projet.usermanagement.serviceImp.AuthenticationService;
import com.projet.usermanagement.serviceImp.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        User u = userService.getUserbymail(e.getEmail());
        if ( u   != null && u.getEnable() != false ) {

                String token = UUID.randomUUID().toString();
                // System.out.println("test 3");
                // System.out.println( e.getEmail() );
                // System.out.println("end of test 3");

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
