package org.iesalixar.daw2.josemiguelgarcialopez.dwese_outfit_webapp.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "outfits")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Outfit {

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

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDate fechaCreacion;

    // Relación con TipoDeRopa
    @ManyToOne
    @JoinColumn(name = "tipo_de_ropa_id", referencedColumnName = "id", nullable = true)  // Es nullable porque puede ser null
    private TipoDeRopa tipoDeRopa;

    public Outfit(String nombre, String descripcion, LocalDate fechaCreacion, TipoDeRopa tipoDeRopa) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaCreacion = fechaCreacion;
        this.tipoDeRopa = tipoDeRopa;
    }
}
