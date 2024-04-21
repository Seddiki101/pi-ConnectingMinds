package com.projet.usermanagement.controller;

import com.projet.usermanagement.dto.IdRequest;
import com.projet.usermanagement.dto.SearchCriteria;
import com.projet.usermanagement.entity.User;
import com.projet.usermanagement.serviceImp.UserSearchService;
import com.projet.usermanagement.serviceImp.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ListUser {

    @Autowired
    private UserServiceImp userService;

    @Autowired
    private UserSearchService userSearchService ;

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
        System.out.println("the received request " + idRequest );
        userService.blockUser( idRequest.getId() );
    //
    return "Request is being processed";
    }


    @PostMapping("/searchUsers")
    public List<User> searchUsers(@RequestBody SearchCriteria criteria) {
        System.out.println("search called "+criteria.getKeyword() );
        return userSearchService.findUsersByKeyword(criteria.getKeyword());
    }



    /*
    @GetMapping("/searchUsers")
    public List<User> searchUsers(@RequestParam String keyword) {
        return userSearchService.findUsersByKeyword(keyword);
    }
    */


}
