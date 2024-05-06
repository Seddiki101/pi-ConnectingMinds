package com.projet.usermanagement.service;

import com.projet.usermanagement.dto.AuthenticationResponse;
import com.projet.usermanagement.dto.RegistrationRequest;
import com.projet.usermanagement.entity.User;

public interface AuthenticationService {

    AuthenticationResponse register(RegistrationRequest request);

    AuthenticationResponse authenticate(User request);

    String confirmToken(String token);

    AuthenticationResponse authenticateById(Long id);

}
