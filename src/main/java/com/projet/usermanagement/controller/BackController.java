package com.projet.usermanagement.controller;

import com.projet.usermanagement.dto.SearchCriteria;
import com.projet.usermanagement.entity.User;
import com.projet.usermanagement.service.UserSearchService;
import com.projet.usermanagement.serviceImp.UserServiceImp;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2/user/back")
public class BackController {

    @Autowired
    private UserServiceImp userService;
    @Autowired
    private UserSearchService userSearchService ;


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



    @GetMapping("/getUserSpot")
    public ResponseEntity<Long> getUserId(@RequestHeader("Authorization") String token) {
        System.out.println("User back request received.");
        token = token.substring(7);
        User user = userService.getUserByToken(token);
        if (user != null) {
            return ResponseEntity.ok(user.getUserId());
        }
        return ResponseEntity.notFound().build();
    }




    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/ids")
    public ResponseEntity<List<User>> getUsersByIds(@RequestBody List<Long> ids) {
        List<User> users = userService.getUsersByIds(ids);
        return ResponseEntity.ok(users);
    }

    @PostMapping("/searchUsers1")
    public List<User> searchUsers1(@RequestBody SearchCriteria criteria) {
        return userSearchService.findUsersByKeyword(criteria.getKeyword());
    }

    @GetMapping("/testing")
    public String testing(HttpServletRequest request){
        System.out.println("Requested URL: " + request.getRequestURL().toString());
        return "testing";
    }

    @GetMapping("/demo")
    public ResponseEntity<String> demo() {
        return ResponseEntity.ok("Hello from secured url");
    }

    @GetMapping("/admin_only")
    public ResponseEntity<String> adminOnly() {
        return ResponseEntity.ok("Hello from admin only url");
    }


}
