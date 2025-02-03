package org.iesalixar.daw2.josemiguelgarcialopez.dwese_outfit_webapp.repositories;

import org.iesalixar.daw2.josemiguelgarcialopez.dwese_outfit_webapp.entities.Outfit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para la entidad Outfit que extiende JpaRepository.
 * Proporciona operaciones CRUD y consultas personalizadas para la entidad Outfit.
 */
public interface OutfitRepository extends JpaRepository<Outfit, Long> {

    /**
     * Obtiene todos los outfits.
     *
     * @return una lista de todos los outfits.
     */
    List<Outfit> findAll();

    /**
     * Inserta o actualiza un outfit.
     *
     * @param outfit la entidad Outfit a insertar o actualizar.
     * @return la entidad Outfit insertada o actualizada.
     */
    Outfit save(Outfit outfit);

    /**
     * Elimina un outfit por su ID.
     *
     * @param id el ID del outfit a eliminar.
     */
    void deleteById(Long id);

    /**
     * Obtiene un outfit por su ID.
     *
     * @param id el ID del outfit.
     * @return un Optional que contiene el outfit si se encuentra, o vacío si no se encuentra.
     */
    Optional<Outfit> findById(Long id);

    /**
     * Comprueba si existe un outfit con un nombre específico.
     *
     * @param nombre el nombre del outfit.
     * @return true si existe un outfit con el nombre especificado, false en caso contrario.
     */
    boolean existsByNombre(String nombre);
    public List<Outfit> findByTipoDeRopaId(Long tipoDeRopaId);


    /**
     * Comprueba si existe un outfit con un nombre específico, excluyendo un outfit por su ID.
     *
     * @param nombre el nombre del outfit.
     * @param id el ID del outfit a excluir.
     * @return true si existe un outfit con el nombre especificado (excluyendo el outfit con el ID dado), false en caso contrario.
     */
    @Query("SELECT COUNT(o) > 0 FROM Outfit o WHERE o.nombre = :nombre AND o.id != :id")
    boolean existsOutfitByNombreAndNotId(@Param("nombre") String nombre, @Param("id") Long id);
}
