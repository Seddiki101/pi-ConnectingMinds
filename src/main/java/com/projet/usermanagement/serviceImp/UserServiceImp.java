package com.projet.usermanagement.serviceImp;

import com.projet.usermanagement.dao.UserRepository;
import com.projet.usermanagement.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImp {

    @Autowired
    private final UserRepository userRepository;

    public User getUserbymail(String email)
    {
        return userRepository.findUserByEmail(email).orElse(null);
       // return userRepository.findUserByEmail(email).get();
    }

    public void updateResetPasswordToken(String token, String email) {
        System.out.println(email);
        email = email.replace("\"","");
        User user = userRepository.findUserByEmail(email).orElse(null);
        if(user != null) {
            user.setResetPasswordToken(token);
            userRepository.save(user);
        }
    }


    public User getByResetPasswordToken(String token)  {
        return userRepository.findByResetPasswordToken(token).orElse(null);
    }


    public void updatePassword(User user, String newPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);

        user.setResetPasswordToken(null);
        userRepository.save(user);
    }


}
