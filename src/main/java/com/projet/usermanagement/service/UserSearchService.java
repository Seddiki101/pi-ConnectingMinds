package com.projet.usermanagement.service;

import com.projet.usermanagement.entity.User;

import java.util.List;

public interface UserSearchService {

    public void fullIndexation();
    public List<User> findAllUsersByKeyword(String keyword);
    public List<User> findUsersByKeyword(String keyword);
    public List<User> findAdminsByKeyword(String keyword);

}
