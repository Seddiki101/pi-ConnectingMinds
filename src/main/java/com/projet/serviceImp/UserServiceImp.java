package com.projet.serviceImp;

import com.projet.dao.UserDao;
import com.projet.entity.User;
import com.projet.service.UserService;
import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImp implements UserService {
    @Resource
    private final UserDao userDao ;

    private final String USER_NOT_FOUND_MSG = " user with email %s cannot be found " ;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userDao.findByEmail(email)
                .orElseThrow( () ->  new UsernameNotFoundException( String.format(USER_NOT_FOUND_MSG,email) ) );
    }

    @Override
    public List<User> getAllUsers() {
        return this.userDao.findAll();
    }

    @Override
    public User findById(Long id) {

        if ( id != null) {
            Optional<User> user = this.userDao.findById(id);
        }
        return null;
    }

}
