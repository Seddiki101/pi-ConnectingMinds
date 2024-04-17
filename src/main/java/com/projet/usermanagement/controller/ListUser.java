package com.projet.usermanagement.controller;

import com.projet.usermanagement.dto.IdRequest;
import com.projet.usermanagement.entity.User;
import com.projet.usermanagement.serviceImp.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ListUser {

    @Autowired
    private UserServiceImp userService;

    @RequestMapping("/admin_only/getAllUsers")
    @GetMapping
    public List<User> getAll()
    {
        return userService.getAllUsers();
    }


    @RequestMapping("/admin_only/BlockUser")
    @PostMapping
    public String blockUser(@RequestBody IdRequest idRequest)
    {
        userService.blockUser( idRequest.getId() );
    //
    return "Request is being processed";
    }


}