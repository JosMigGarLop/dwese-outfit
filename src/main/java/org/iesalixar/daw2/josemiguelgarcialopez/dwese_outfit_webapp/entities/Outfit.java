package org.iesalixar.daw2.josemiguelgarcialopez.dwese_outfit_webapp.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Date;

/**
 * La clase `Outfit` representa una entidad que modela un conjunto de ropa dentro de la base de datos.
 * Contiene cuatro campos: `nombre`, `descripcion`, `imagen` y `fechaCreacion`.
 */
@Entity
@Table(name = "outfits")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"tipoDeRopa"})  // Excluye la relación innecesaria para evitar bucles infinitos
@EqualsAndHashCode(exclude = {"tipoDeRopa"})  // Excluye la relación innecesaria
public class Outfit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // La base de datos genera el ID automáticamente
    private Long id;

    @NotEmpty(message = "El nombre no puede estar vacío.")
    @Size(max = 100, message = "El nombre debe tener como máximo 100 caracteres.")
    @Column(name = "nombre", nullable = false, length = 100)  // La columna en la base de datos se llama "nombre"
    private String nombre;

    @Size(max = 255, message = "La descripción debe tener como máximo 255 caracteres.")
    @Column(name = "descripcion", length = 255)  // La columna en la base de datos se llama "descripcion"
    private String descripcion;

    @Size(max = 500, message = "La URL de la imagen no debe superar los 500 caracteres.")
    @Column(name = "imagen", length = 500)  // No es necesario especificar nullable=true, ya que es true por defecto
    private String imagen;

    @Column(name = "fecha_creacion", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)  // Asegura que se guarde como timestamp con fecha y hora
    private Date fechaCreacion;

    // Relación ManyToOne con TipoDeRopa (un Outfit tiene un TipoDeRopa)
    @ManyToOne
    @JoinColumn(name = "tipo_de_ropa_id", nullable = false)  // La columna que se usará como clave foránea en la base de datos
    private TipoDeRopa tipoDeRopa;

    /**
     * Constructor personalizado para crear un Outfit sin el ID.
     * @param nombre Nombre del outfit.
     * @param descripcion Descripción del outfit.
     * @param imagen URL de la imagen asociada al outfit.
     * @param fechaCreacion Fecha de creación del outfit.
     * @param tipoDeRopa Tipo de ropa asociada al outfit.
     */
    public Outfit(String nombre, String descripcion, String imagen, Date fechaCreacion, TipoDeRopa tipoDeRopa) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.fechaCreacion = fechaCreacion;
        this.tipoDeRopa = tipoDeRopa;
    }

}
