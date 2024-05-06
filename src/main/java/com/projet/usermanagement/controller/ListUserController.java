package com.projet.usermanagement.controller;

import com.projet.usermanagement.dto.EmailRequest;
import com.projet.usermanagement.dto.IdRequest;
import com.projet.usermanagement.dto.SearchCriteria;
import com.projet.usermanagement.entity.User;
import com.projet.usermanagement.serviceImp.UserSearchService;
import com.projet.usermanagement.serviceImp.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ListUserController {

    @Autowired
    private UserServiceImp userService;

    @Autowired
    private UserSearchService userSearchService ;

    @RequestMapping("/admin_only/getAllUsers")
    @GetMapping
    public List<User> getAll()
    {
        return userService.getAllRegularUsers();
        //return userService.getAllUsers();
    }


    @RequestMapping("/admin_only/getAllAdmins")
    @GetMapping
    public List<User> getAllAdmins()
    {
        return userService.getAllAdminUsers();
    }

/*
    @RequestMapping("/admin_only/BlockUser")
    @PostMapping
    public String blockUser(@RequestBody IdRequest idRequest)
    {
        System.out.println("the received request " + idRequest );
        userService.blockUser( idRequest.getId() );
    return "Request is being processed";
    }
    */



    @RequestMapping("/admin_only/BlockUser")
    @PostMapping
    public String blockUser2(@RequestBody EmailRequest email)
    {
        userService.blockUser2( email.getEmail() );
        return "Request is being processed";
    }


    @RequestMapping("/admin_only/RevokeUser")
    @PostMapping
    public String revokeUser(@RequestBody EmailRequest email)
    {
        userService.revokeUser( email.getEmail() );
        return "Request is being processed";
    }

    @RequestMapping("/admin_only/giveAccess")
    @PostMapping
    public String giveAccess(@RequestBody EmailRequest email)
    {
        userService.giveAcceess( email.getEmail() );
        return "Request is being processed";
    }


    @PostMapping("/admin_only/searchUsers")
    public List<User> searchUsers(@RequestBody SearchCriteria criteria) {
     //   System.out.println("search called "+criteria.getKeyword() );
        return userSearchService.findUsersByKeyword(criteria.getKeyword());
    }


    @PostMapping("/admin_only/searchUsers1")
    public List<User> searchUsers1(@RequestBody SearchCriteria criteria) {
      //  System.out.println("search called "+criteria.getKeyword() );
        return userSearchService.findUsersByKeyword1(criteria.getKeyword());
    }

    @PostMapping("/admin_only/searchUser")
    public List<User> searchUser(@RequestBody SearchCriteria criteria) {
        return userSearchService.findUsersByKeyword2(criteria.getKeyword());
    }



}
