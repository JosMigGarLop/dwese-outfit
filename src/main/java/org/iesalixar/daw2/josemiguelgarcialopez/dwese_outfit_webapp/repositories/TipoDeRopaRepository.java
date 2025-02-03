package org.iesalixar.daw2.josemiguelgarcialopez.dwese_outfit_webapp.repositories;

import org.iesalixar.daw2.josemiguelgarcialopez.dwese_outfit_webapp.entities.TipoDeRopa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para la entidad TipoDeRopa que extiende JpaRepository.
 * Proporciona operaciones CRUD y consultas personalizadas para la entidad TipoDeRopa.
 */
public interface TipoDeRopaRepository extends JpaRepository<TipoDeRopa, Long> {

    /**
     * Obtiene todos los tipos de ropa.
     *
     * @return una lista de todos los tipos de ropa.
     */
    List<TipoDeRopa> findAll();

    /**
     * Inserta o actualiza un tipo de ropa.
     *
     * @param tipoDeRopa la entidad TipoDeRopa a insertar o actualizar.
     * @return la entidad TipoDeRopa insertada o actualizada.
     */
    TipoDeRopa save(TipoDeRopa tipoDeRopa);

    /**
     * Elimina un tipo de ropa por su ID.
     *
     * @param id el ID del tipo de ropa a eliminar.
     */
    void deleteById(Long id);

    /**
     * Obtiene un tipo de ropa por su ID.
     *
     * @param id el ID del tipo de ropa.
     * @return un Optional que contiene el tipo de ropa si se encuentra, o vacío si no se encuentra.
     */
    Optional<TipoDeRopa> findById(Long id);

    /**
     * Comprueba si existe un tipo de ropa con un nombre específico.
     *
     * @param nombre el nombre del tipo de ropa.
     * @return true si existe un tipo de ropa con el nombre especificado, false en caso contrario.
     */
    boolean existsByNombre(String nombre);

    /**
     * Comprueba si existe un tipo de ropa con un nombre específico, excluyendo un tipo de ropa por su ID.
     *
     * @param nombre el nombre del tipo de ropa.
     * @param id el ID del tipo de ropa a excluir.
     * @return true si existe un tipo de ropa con el nombre especificado (excluyendo el tipo de ropa con el ID dado), false en caso contrario.
     */
    @Query("SELECT COUNT(t) > 0 FROM TipoDeRopa t WHERE t.nombre = :nombre AND t.id != :id")
    boolean existsTipoDeRopaByNombreAndNotId(@Param("nombre") String nombre, @Param("id") Long id);
}
