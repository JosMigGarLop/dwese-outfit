package org.iesalixar.daw2.josemiguelgarcialopez.dwese_outfit_api.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase DTO (Data Transfer Object) que representa un tipo de ropa.
 *
 * Se utiliza para transferir datos de un tipo de ropa entre las capas de la aplicación,
 * especialmente para exponerlos a través de la API sin incluir información innecesaria.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoDeRopaDTO {

    /**
     * Identificador único del tipo de ropa.
     */
    private Long id;

    /**
     * Nombre del tipo de ropa.
     */
    private String nombre;

    /**
     * Descripción del tipo de ropa.
     */
    private String descripcion;
}
