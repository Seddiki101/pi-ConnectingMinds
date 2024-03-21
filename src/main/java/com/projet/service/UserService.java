package com.projet.service;

import com.projet.entity.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User findById (Integer id);
}
