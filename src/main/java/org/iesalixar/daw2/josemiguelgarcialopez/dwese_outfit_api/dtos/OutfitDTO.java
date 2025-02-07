package org.iesalixar.daw2.josemiguelgarcialopez.dwese_outfit_api.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Clase DTO (Data Transfer Object) que representa un outfit.
 *
 * Se utiliza para transferir datos de un outfit entre las capas de la aplicación,
 * especialmente para exponerlos a través de la API sin incluir información innecesaria.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OutfitDTO {

    /**
     * Identificador único del outfit.
     */
    private Long id;

    /**
     * Nombre del outfit.
     */
    private String nombre;

    /**
     * Descripción del outfit.
     */
    private String descripcion;

    /**
     * Fecha de creación del outfit.
     */
    private LocalDate fechaCreacion;

    /**
     * Información del tipo de ropa asociado al outfit.
     * Se incluye el nombre del tipo de ropa en lugar del ID para mejorar la legibilidad en las respuestas de la API.
     */
    private String tipoDeRopaNombre;
}
