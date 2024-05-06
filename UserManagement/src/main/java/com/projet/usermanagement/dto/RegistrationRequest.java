package com.projet.usermanagement.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class RegistrationRequest {
    //might change later
    // if they give error -> add allAgrgsCtor annotation
    private final String email;
    private final String password;
    private final String firstName;
    private final String lastName;
    private final String phone;
    private final String address;
    private final Date birthdate;


}
