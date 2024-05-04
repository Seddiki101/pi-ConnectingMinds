package com.projet.chatmanagement.client;


import com.projet.chatmanagement.dto.user.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service", url = "${application.config.user}")
public interface UserClient {
    @GetMapping("/api/v2/user/back/{id}")
    UserDTO findUserById(@PathVariable Long id);
}
