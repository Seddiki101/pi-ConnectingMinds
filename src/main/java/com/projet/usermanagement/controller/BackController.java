package com.projet.usermanagement.controller;

import com.projet.usermanagement.entity.User;
import com.projet.usermanagement.serviceImp.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BackController {

    @Autowired
    private UserServiceImp userService;


    @RequestMapping("/getUserData")
    @GetMapping
    public User getProfile(@RequestHeader("Authorization") String token) {
        System.out.println("user back request ");
        token = token.substring(7);
        User user = userService.getUserByToken(token);
        if (user != null) {
            return user;
        }
        return null;
    }


}
