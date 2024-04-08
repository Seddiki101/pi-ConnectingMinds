package com.projet.usermanagement.serviceImp;



import com.projet.usermanagement.dao.TokenRepository;
import com.projet.usermanagement.dao.UserRepository;
import com.projet.usermanagement.dto.AuthenticationResponse;
import com.projet.usermanagement.dto.RegistrationRequest;
import com.projet.usermanagement.emailer.EmailSender;
import com.projet.usermanagement.entity.User;
import com.projet.usermanagement.entity.UserRole;
import com.projet.usermanagement.security.Token;
import com.projet.usermanagement.security.ConfirmationToken;
import com.projet.usermanagement.serviceImp.ConfirmationTokenService;
import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service

public class AuthenticationService {
@Autowired
    private  UserRepository repository;
    @Autowired
    private  PasswordEncoder passwordEncoder;
    @Autowired
    private  JwtService jwtService;
    @Resource
    private  ConfirmationTokenService confirmationTokenService  ;
    @Autowired
    private  TokenRepository tokenRepository;
    @Autowired
    private  AuthenticationManager authenticationManager;

    @Autowired
    private EmailSender emailSender;


    private static final String EMAIL_PATTERN =
            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);
/*
    public AuthenticationService(UserRepository repository,
                                 PasswordEncoder passwordEncoder,
                                 JwtService jwtService, ConfirmationTokenService confirmationTokenService,
                                 TokenRepository tokenRepository,
                                 AuthenticationManager authenticationManager, EmailSender emailSender) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.confirmationTokenService = confirmationTokenService;
        this.tokenRepository = tokenRepository;
        this.authenticationManager = authenticationManager;
        this.emailSender = emailSender;
    }
    */

    private boolean validateEmail(String email) {
        if (email == null) {
            return false;
        }
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


    public AuthenticationResponse register(RegistrationRequest request) {

        if (!validateEmail(request.getEmail())) {
            return new AuthenticationResponse(null, "email is not valid ");
            // throw new IllegalStateException("Your email is not valid");
        } else {

            // check if user already exist. if exist than authenticate the user
            if (repository.findUserByEmail(request.getEmail()).isPresent()) {
                return new AuthenticationResponse(null, "email taken");
            }

            User user = new User();
            user.setFirstName(request.getFirstName());
            user.setLastName(request.getLastName());
            user.setUsername(request.getEmail());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setRole(UserRole.USER);
            Date date = new Date() ;
            user.setCreatedAt(date);

            user = repository.save(user);

            String jwt = jwtService.generateToken(user);

            saveUserToken(jwt, user);

            // email verification ---------------------
            String tokenConfirm = UUID.randomUUID().toString();

            ConfirmationToken confirmationToken = new ConfirmationToken(
                    tokenConfirm,
                    LocalDateTime.now(),
                    LocalDateTime.now().plusMinutes(15),
                    user
            );

            confirmationTokenService.saveConfirmationToken(
                    confirmationToken);

            String link = "http://localhost:8082/confirmEmail?token=" + tokenConfirm;
            emailSender.sendConfirmation(
                    request.getEmail(),
                    emailSender.buildConfirmEmail(request.getFirstName(), link));

            // email verification ---------------------

            return new AuthenticationResponse(jwt, "User registration was successful");
        }//mail valid
    }

    public AuthenticationResponse authenticate(User request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        User user = repository.findUserByEmail(request.getUsername()).orElseThrow();
        String jwt = jwtService.generateToken(user);

        revokeAllTokenByUser(user);
        saveUserToken(jwt, user);

        return new AuthenticationResponse(jwt, "User login was successful");

    }
    private void revokeAllTokenByUser(User user) {
        List<Token> validTokens = tokenRepository.findAllTokensByUser(user.getId());
        if(validTokens.isEmpty()) {
            return;
        }

        validTokens.forEach(t-> {
            t.setLoggedOut(true);
        });

        tokenRepository.saveAll(validTokens);
    }
    private void saveUserToken(String jwt, User user) {
        Token token = new Token();
        token.setToken(jwt);
        token.setLoggedOut(false);
        token.setUser(user);
        tokenRepository.save(token);
    }

    /*
    @Transactional
    public String confirmToken(String token) {
        Optional<ConfirmationToken> confirmationTokenOptional = confirmationTokenService.getToken(token);

        // Check if the token was found
        if (!confirmationTokenOptional.isPresent()) {
            System.out.println("token not found");
            return "token not found";
        }

        ConfirmationToken confirmationToken = confirmationTokenOptional.get();

        // Check if the email was already confirmed
        if (confirmationToken.getConfirmedAt() != null) {
            System.out.println("email already confirmed");
            return "email already confirmed";
        }

        // Check if the token has expired
        LocalDateTime expiredAt = confirmationToken.getExpiresAt();
        if (expiredAt.isBefore(LocalDateTime.now())) {
            System.out.println("token expired");
            return "token expired";
        }

        // Proceed to confirm the token and enable the app user
        confirmationTokenService.setConfirmedAt(token);
        repository.enableAppUser(confirmationToken.getAppUser().getEmail());

        System.out.println("confirmed");
        return "confirmed";
    }
*/




    @Transactional
    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow(() ->
                        new IllegalStateException("token not found"));

        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("email already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }

        confirmationTokenService.setConfirmedAt(token);

        //System.out.println();
         repository.enableAppUser( confirmationToken.getAppUser().getEmail() );

        return "confirmed";
    }





}
