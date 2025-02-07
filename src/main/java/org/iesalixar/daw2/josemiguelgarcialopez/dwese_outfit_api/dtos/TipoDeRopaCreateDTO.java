package org.iesalixar.daw2.josemiguelgarcialopez.dwese_outfit_api.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

/**
 * DTO (Data Transfer Object) utilizado para la creación de nuevos tipos de ropa.
 *
 * Este DTO asegura que los datos necesarios para crear un tipo de ropa
 * sean válidos y cumplan con las restricciones definidas, como no permitir valores nulos
 * o limitar el tamaño de los campos.
 *
 * Diseñado para recibir datos en peticiones HTTP POST en la API.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoDeRopaCreateDTO {

    /**
     * Nombre del tipo de ropa.
     *
     * - No puede estar vacío (`@NotEmpty`).
     * - Longitud máxima de 50 caracteres (`@Size(max = 50)`).
     */
    @NotEmpty(message = "El nombre no puede estar vacío")
    @Size(max = 50, message = "El nombre no puede tener más de 50 caracteres")
    private String nombre;

    /**
     * Descripción del tipo de ropa.
     *
     * - No puede estar vacía (`@NotEmpty`).
     * - Longitud máxima de 255 caracteres (`@Size(max = 255)`).
     */
    @NotEmpty(message = "La descripción no puede estar vacía")
    @Size(max = 255, message = "La descripción no puede tener más de 255 caracteres")
    private String descripcion;
}
