package com.projet.serviceImp;

import com.projet.service.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationServiceImp implements RegistrationService {
    @Override
    public String register(RegistrationRequest request) {
        return "tempo ";
    }
}
