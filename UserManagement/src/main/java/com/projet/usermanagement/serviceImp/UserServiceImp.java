package com.projet.usermanagement.serviceImp;

import com.projet.usermanagement.dao.UserRepository;
import com.projet.usermanagement.dto.AuthenticationResponse;
import com.projet.usermanagement.entity.User;
import com.projet.usermanagement.entity.UserRole;
import com.projet.usermanagement.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.projet.usermanagement.dto.RegistrationRequest;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import com.projet.usermanagement.dto.AuthenticationResponse;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImp implements UserService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private UserValidator userValidator;
 @Autowired
 private PasswordEncoder passwordEncoder;


    public List<User> getAllUsers()
    {
        //maybe create a method in repo to get certain attributes
        return userRepository.findAll();
    }


    public List<User> getAllRegularUsers()
    {
        return userRepository.findByRole(UserRole.USER);
    }

    public List<User> getAllAdminUsers()
    {
        return userRepository.findByRole(UserRole.ADMIN  );
    }

    public User getUserById(Long id)
    {
        return userRepository.findUserById(id).orElse(null);
    }



    public User getUserbymail(String email)
    {
        return userRepository.findUserByEmail(email).orElse(null);
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
    public void blockUser2(String email)
    {
        userRepository.blockUser2(email);
    }

    public void revokeUser(String email)
    {
        userRepository.revokeUser(email);
    }

    public void giveAcceess(String email){ userRepository.giveAccess(email);}


    public void updateUser(User user,RegistrationRequest request)
    {

        Errors errors = new BeanPropertyBindingResult(request, "registrationRequest");
        userValidator.validate(request, errors);


        if ( ! errors.hasErrors() ) {

            user.setFirstName(request.getFirstName());
            user.setLastName(request.getLastName());
            user.setEmail(request.getEmail());
            user.setPhone(request.getPhone());
            user.setAddress(request.getAddress());
            if (  request.getPassword().length() > 8 ){
                String encodedPassword = passwordEncoder.encode(request.getPassword());
            System.out.println("the new password is " + request.getPassword());
            user.setPassword(encodedPassword);
        }
            userRepository.save(user);
        }


    }


    public List<User> getUsersByIds(List<Long> ids) {
        return userRepository.findByIdIn(ids);
    }


}
