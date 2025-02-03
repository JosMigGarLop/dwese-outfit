package org.iesalixar.daw2.josemiguelgarcialopez.dwese_outfit_webapp.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "tipos_de_ropa")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class TipoDeRopa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "El nombre no puede estar vacío")
    @Size(max = 50, message = "El nombre no puede tener más de 50 caracteres")
    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;

    @NotEmpty(message = "La descripción no puede estar vacía")
    @Size(max = 255, message = "La descripción no puede tener más de 255 caracteres")
    @Column(name = "descripcion", nullable = false, length = 255)
    private String descripcion;

    public TipoDeRopa(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }
}