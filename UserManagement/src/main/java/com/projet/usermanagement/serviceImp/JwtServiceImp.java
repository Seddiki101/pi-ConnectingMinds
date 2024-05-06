package com.projet.usermanagement.serviceImp;

import com.projet.usermanagement.dao.TokenRepository;
import com.projet.usermanagement.entity.User;
import com.projet.usermanagement.entity.UserRole;
import com.projet.usermanagement.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtServiceImp implements JwtService {

    private final String SECRET_KEY = "4bb6d1dfbafb64a681139d1586b6f1160d18159afd57c8c79136d7490630407c";
    @Autowired
    private TokenRepository tokenRepository;
/*
    public JwtServiceImp(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    } */
    @Override

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public boolean isValid(String token, UserDetails user) {
        String username = extractUsername(token);

        boolean validToken = tokenRepository
                .findByToken(token)
                .map(t -> !t.isLoggedOut())
                .orElse(false);

        return (username.equals(user.getUsername())) && !isTokenExpired(token) && validToken;
    }

    @Override
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    @Override
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    @Override
    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }

    @Override
    public Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(getSigninKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }


    @Override
    public String generateToken(User user) {
        //this doesn t work
        //Map<String, Object> claims = new HashMap<>();
        //claims.put("role", user.getRole().name());
        //claims.put("guise",role);
        //claims.put("firstname", user.getFirstName());
        //  .setClaims(claims)
        //

        //this if you want to test role in front
        String role ="420" ;
        if( user.getRole().name().equals(UserRole.ADMIN.toString() ) ) { role = "69" ;  }



        String token = Jwts
                .builder()
                .subject(user.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 24*60*60*1000 ))
                .claim("guise", role)
                .claim("firstname", user.getFirstName())
                .claim("lastname", user.getLastName())

                .signWith(getSigninKey())
                .compact();

        return token;
    }

    @Override
    public SecretKey getSigninKey() {
        byte[] keyBytes = Decoders.BASE64URL.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
