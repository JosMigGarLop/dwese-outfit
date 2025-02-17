package org.iesalixar.daw2.josemiguelgarcialopez.dwese_outfit_api.controllers;

import org.iesalixar.daw2.josemiguelgarcialopez.dwese_outfit_api.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/email")
public class EmailController {
    @Autowired
    private EmailService emailService;

    @PostMapping("/send")
    public String sendEmail(
            @RequestParam String to,
            @RequestParam String subject,
            @RequestParam String text) {
        emailService.sendMail(to, subject, text);
        return "Correo enviado exitosamente a " + to;
    }
}