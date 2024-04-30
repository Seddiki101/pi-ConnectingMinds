package com.projet.usermanagement.serviceImp;
import com.projet.usermanagement.dto.RegistrationRequest;
import com.projet.usermanagement.entity.User;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import java.util.Date;
import java.util.Calendar;



@Component
public class UserValidator implements Validator {

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$";
    private static final String PHONE_REGEX = "^\\d+$";

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz) || RegistrationRequest.class.equals(clazz);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        if (obj instanceof User) {
            validateUser((User) obj, errors);
        } else if (obj instanceof RegistrationRequest) {
            validateRegistrationRequest((RegistrationRequest) obj, errors);
        }
    }

    private void validateUser(User user, Errors errors) {
        // Existing validation logic for User...
    }

    private void validateRegistrationRequest(RegistrationRequest request, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "field.required", "First name is required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "field.required", "Last name is required");

        if (request.getFirstName().length() < 3) {
            errors.rejectValue("firstName", "firstName.short", "First name must be at least 3 characters long");
        }

        if (request.getLastName().length() < 3) {
            errors.rejectValue("lastName", "lastName.short", "Last name must be at least 3 characters long");
        }

        if (!request.getEmail().matches(EMAIL_REGEX)) {
            errors.rejectValue("email", "email.invalid", "Email address is not valid");
        }

        if (!request.getPhone().matches(PHONE_REGEX)) {
            errors.rejectValue("phone", "phone.invalid", "Phone number must be numeric");
        }

        if (request.getPassword() == null || request.getPassword().length() < 8) {
            errors.rejectValue("password", "password.short", "Password must be at least 8 characters long");
        }

        if (request.getAddress() != null && request.getAddress().length() < 4) {
            errors.rejectValue("address", "address.short", "Address must be at least 4 characters long");
        }

        if (!isAdult(request.getBirthdate())) {
            errors.rejectValue("birthdate", "birthdate.invalid", "You must be over the age of 17 to register");
        }
    }

    private boolean isAdult(Date birthDate) {
        Calendar birth = Calendar.getInstance();
        birth.setTime(birthDate);
        Calendar today = Calendar.getInstance();
        int age = today.get(Calendar.YEAR) - birth.get(Calendar.YEAR);
        if (today.get(Calendar.DAY_OF_YEAR) < birth.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }
        return age > 17;
    }
}

/*
@Component
public class UserValidator implements Validator {

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$";
    private static final String PHONE_REGEX = "^\\d+$";

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        User user = (User) obj;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "field.required", "First name is required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "field.required", "Last name is required");

        if (user.getFirstName().length() < 3) {
            errors.rejectValue("firstName", "firstName.short", "First name must be at least 3 characters long");
        }

        if (user.getLastName().length() < 3) {
            errors.rejectValue("lastName", "lastName.short", "Last name must be at least 3 characters long");
        }

        if (!user.getEmail().matches(EMAIL_REGEX)) {
            errors.rejectValue("email", "email.invalid", "Email address is not valid");
        }

        if (!user.getPhone().matches(PHONE_REGEX)) {
            errors.rejectValue("phone", "phone.invalid", "Phone number must be numeric");
        }

        if (user.getPassword() == null || user.getPassword().length() < 8) {
            errors.rejectValue("password", "password.short", "Password must be at least 8 characters long");
        }

        if (user.getAddress() != null && user.getAddress().length() < 4) {
            errors.rejectValue("address", "address.short", "Address must be at least 4 characters long");
        }

        if (!isAdult(user.getBirthdate())) {
            errors.rejectValue("birthdate", "birthdate.invalid", "You must be over the age of 17 to register");
        }
    }

    private boolean isAdult(Date birthDate) {
        Calendar birth = Calendar.getInstance();
        birth.setTime(birthDate);
        Calendar today = Calendar.getInstance();
        int age = today.get(Calendar.YEAR) - birth.get(Calendar.YEAR);
        if (today.get(Calendar.DAY_OF_YEAR) < birth.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }
        return age > 17;
    }
}
*/