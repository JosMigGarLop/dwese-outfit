package org.iesalixar.daw2.josemiguelgarcialopez.dwese_outfit_webapp.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

/**
 * La clase `TipoDeRopa` representa una entidad que modela los tipos de ropa dentro de la base de datos.
 * Contiene tres campos: `nombre`, `descripcion` e `imagen`.
 */
@Entity
@Table(name = "tipo_de_ropa")  // Asegúrate de que el nombre de la tabla sea correcto en la base de datos.
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"outfits"})  // Excluye la relación innecesaria para evitar ciclos recursivos
@EqualsAndHashCode(exclude = {"outfits"})  // Excluye la relación innecesaria para evitar problemas de recursión
public class TipoDeRopa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // El ID se genera automáticamente por la base de datos
    private Long id;

    @NotEmpty(message = "{msg.tipoDeRopa.nombre.notEmpty}")
    @Size(min = 2, max = 100, message = "{msg.tipoDeRopa.nombre.size}")
    @Column(name = "nombre", nullable = false, length = 100)  // Asegúrate de que el nombre del campo en la base de datos es correcto
    private String nombre;

    @Size(max = 255, message = "{msg.tipoDeRopa.descripcion.size}")
    @Column(name = "descripcion", length = 255)  // Asegúrate de que la longitud del campo en la base de datos es suficiente
    private String descripcion;

    @Size(max = 500, message = "{msg.tipoDeRopa.imagen.size}")
    @Column(name = "imagen", length = 500)  // No es necesario especificar nullable=true ya que por defecto es true.
    private String imagen;

    // Relación OneToMany con Outfit. Asegúrate de que en la clase Outfit haya una relación inversa.
    @OneToMany(mappedBy = "tipoDeRopa", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Outfit> outfits;

    // Constructor sin ID, para cuando no queremos establecer un ID antes de la persistencia
    public TipoDeRopa(String nombre, String descripcion, String imagen) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.imagen = imagen;
    }
}
