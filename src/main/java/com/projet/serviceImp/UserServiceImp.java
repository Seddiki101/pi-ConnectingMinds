package com.projet.serviceImp;

import com.projet.dao.UserDao;
import com.projet.entity.User;
import com.projet.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImp implements UserService {
    @Resource
    private UserDao userDao ;

    @Override
    public List<User> getAllUsers() {
        return this.userDao.findAll();
    }

    @Override
    public User findById(Integer id) {

        if ( id != null) {
            Optional<User> user = this.userDao.findById(id);
        }
        return null;
    }
}
