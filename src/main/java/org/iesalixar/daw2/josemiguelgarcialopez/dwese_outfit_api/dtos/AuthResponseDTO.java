package org.iesalixar.daw2.josemiguelgarcialopez.dwese_outfit_api.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponseDTO {

    private String token;
    private String message;
}

