//package com.esprit.resourcesmanagement.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.JavaMailSenderImpl;
//
//
//
//import java.util.Properties;
//
//@Configuration
//public class EmailConfig {
//    @Bean
//    public JavaMailSender javaMailSender() {
//        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//        mailSender.setHost("smtp.gmail.com"); // Serveur SMTP
//        mailSender.setPort(587); // Port du serveur SMTP (ex. 587 pour TLS)
//        mailSender.setUsername("raniabrahmi2607@gmail.com"); // Identifiant de connexion au serveur SMTP
//        mailSender.setPassword("223JFT0049"); // Mot de passe de connexion au serveur SMTP
//
//        Properties props = mailSender.getJavaMailProperties();
//        props.put("mail.transport.protocol", "smtp"); // Protocole SMTP
//        props.put("mail.smtp.auth", "true"); // Authentification requise
//        props.put("mail.smtp.starttls.enable", "true"); // Utilisation de TLS
//
//        return mailSender;
//    }
//}
