package com.projet.usermanagement.emailer;

public interface EmailSender {
    void sendConfirmation(String to, String email);
     String buildConfirmEmail(String name, String link);
    public void sendForgetPasswordEmail(String recipientEmail, String link) ;

    }
