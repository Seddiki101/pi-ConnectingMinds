package com.projet.usermanagement.service;

import com.projet.usermanagement.dto.RegistrationRequest;
import com.projet.usermanagement.entity.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    User getUserbymail(String email);

    User getByResetPasswordToken(String token);

    User getUserByToken(String token);

    void updateResetPasswordToken(String token, String email);

    void updatePassword(User user, String newPassword);

    void blockUser(Long id);
     void updateUser(User user, RegistrationRequest request);

}
