package com.projet.serviceImp;

import lombok.*;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class RegistrationRequest {
    //might change later
    // if they give error -> add allAgrgsCtor annotation
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String password;
}
