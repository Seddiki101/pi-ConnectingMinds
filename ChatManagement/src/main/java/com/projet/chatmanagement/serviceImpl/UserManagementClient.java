package com.projet.chatmanagement.serviceImpl;

import com.projet.chatmanagement.dto.user.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
@Service
public class UserManagementClient {

    private final RestTemplate restTemplate;

    public UserManagementClient() {
        this.restTemplate = new RestTemplate();
    }

    private static final Logger log = LoggerFactory.getLogger(UserManagementClient.class);


    public UserDTO fetchUserById(Long userId, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        HttpEntity<?> entity = new HttpEntity<>(headers);

        String url = "http://localhost:8082/api/v2/user/back/" + userId;
        System.out.println(url);
        try {
            ResponseEntity<UserDTO> response = restTemplate.exchange(url, HttpMethod.GET, entity, UserDTO.class);
            return response.getBody();
        } catch (Exception e) {
            log.error("Failed to fetch user details", e);
            throw e;
        }
    }



    /*
    public UserDTO fetchUserById(Long userId, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        String url = "http://localhost:8082/back/user/" + userId;
        System.out.println(url);
        try {
            return restTemplate.getForObject(url, UserDTO.class);
        } catch (Exception e) {
            log.error("Failed to fetch user details", e);
            throw e;
        }
    }*/
}
