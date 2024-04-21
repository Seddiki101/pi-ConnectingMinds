package com.projet.usermanagement.serviceImp;

import com.projet.usermanagement.dao.UserRepository;
import com.projet.usermanagement.dto.AuthenticationResponse;
import com.projet.usermanagement.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.projet.usermanagement.dto.RegistrationRequest;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import com.projet.usermanagement.dto.AuthenticationResponse;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImp {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private UserValidator userValidator;

    public List<User> getAllUsers()
    {
        //maybe create a method in repo to get certain attributes
        return userRepository.findAll();
    }

    public User getUserbymail(String email)
    {
        return userRepository.findUserByEmail(email).orElse(null);
       // return userRepository.findUserByEmail(email).get();
    }

    public User getByResetPasswordToken(String token)  {
        return userRepository.findByResetPasswordToken(token).orElse(null);
    }

    public User getUserByToken(String token)  {
        return userRepository.findUserByToken(token).orElse(null);
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



    public void updatePassword(User user, String newPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);

        user.setResetPasswordToken(null);
        userRepository.save(user);
    }

    public void blockUser(Long id)
    {
        userRepository.blockUser(id);
    }


    public void updateUser(User user,RegistrationRequest request)
    {

        Errors errors = new BeanPropertyBindingResult(request, "registrationRequest");
        userValidator.validate(request, errors);


        if ( ! errors.hasErrors() ) {

            user.setFirstName(request.getFirstName() );
            user.setLastName(request.getLastName() );
            user.setEmail(request.getEmail() );
            user.setPhone(request.getPhone() );
            user.setAddress( request.getAddress() );

            userRepository.save(user);
        }


    }


}
