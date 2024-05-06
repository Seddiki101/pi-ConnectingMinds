package com.projet.usermanagement.service;

import com.projet.usermanagement.dao.ConfirmationTokenDao;
import com.projet.usermanagement.security.ConfirmationToken;

import java.util.Optional;

public interface ConfirmationTokenService {


    public void saveConfirmationToken(ConfirmationToken token);

    public Optional<ConfirmationToken> getToken(String token) ;

    public int setConfirmedAt(String token);

}
