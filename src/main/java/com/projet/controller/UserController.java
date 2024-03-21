package com.projet.controller;

import com.projet.entity.User;
import com.projet.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("users")
public class UserController {

    @Autowired
    UserService userService ;

    @GetMapping("/all-users")
    @ResponseBody
    public List<User> getUsers() {
        return this.userService.getAllUsers();
    }


}
