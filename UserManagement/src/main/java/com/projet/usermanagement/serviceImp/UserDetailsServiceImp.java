package com.projet.usermanagement.serviceImp;

import com.projet.usermanagement.dao.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImp implements UserDetailsService {

    private final UserRepository repository;

    public UserDetailsServiceImp(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String emil) throws UsernameNotFoundException {
        return repository.findUserByEmail(emil)
                .orElseThrow(()-> new UsernameNotFoundException("User not found"));
    }
}
