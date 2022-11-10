package com.adidas.backend.emailservice.controllers;

import com.adidas.backend.emailservice.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

@RestController

public class EmailController {

    @Autowired
    private EmailService emailService;

    /**
     * Triggers the email sending with the selected clients
     * @param email
     * @param name
     * @return boolean
     */
    @PutMapping
    @RequestMapping(path = "/email")
    private boolean sendEmail(@RequestParam(value = "email") final String email,
                              @RequestParam(value = "name") final String name) throws MessagingException {
        return emailService.sendEmail(email, name);
    }
}
