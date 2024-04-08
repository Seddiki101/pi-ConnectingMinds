package com.projet.usermanagement.serviceImp;

import com.projet.usermanagement.dao.UserRepository;
import com.projet.usermanagement.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImp {

    @Autowired
    private final UserRepository userRepository;

    public void updateResetPasswordToken(String token, String email) {
        System.out.println(email);
        email = email.replace("\"","");
        User user = userRepository.findUserByEmail(email).orElse(null);
        if(user != null) {
            user.setResetPasswordToken(token);
            userRepository.save(user);
        }
    }

}
