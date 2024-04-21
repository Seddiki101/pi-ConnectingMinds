package com.projet.usermanagement.controller;

import com.projet.usermanagement.dto.RegistrationRequest;
import com.projet.usermanagement.emailer.EmailSender;
import com.projet.usermanagement.entity.User;
import com.projet.usermanagement.serviceImp.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class ProfileController {

        @Autowired
        private UserServiceImp userService;

    @RequestMapping("/profile")
    @GetMapping
    public User getProfile(@RequestHeader("Authorization") String token) {
        System.out.println("profile request ");
        token = token.substring(7); // Remove "Bearer " prefix
        System.out.println("profile " + token  );
        User user = userService.getUserByToken(token);
        if (user != null) {
            return user; // Consider not sending all attributes
        }
        return null;
    }


    @RequestMapping("pofiledit")
    @PutMapping
    public void updateProfile(@RequestHeader("Authorization") String token , @RequestBody RegistrationRequest request) {
        System.out.println("update profile request ");
        token = token.substring(7); // Remove "Bearer " prefix
        User user = userService.getUserByToken(token);
        if (user != null) {
            userService.updateUser(user,request) ;
        }
    }


}
