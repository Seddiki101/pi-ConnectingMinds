package com.projet.usermanagement.emailer;

public interface EmailSender {
    void sendConfirmation(String to, String email);
     String buildConfirmEmail(String name, String link);
}
