package org.iesalixar.daw2.josemiguelgarcialopez.dwese_outfit_webapp.repositories;

import org.iesalixar.daw2.josemiguelgarcialopez.dwese_outfit_webapp.entities.TipoDeRopa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TipoDeRopaRepository extends JpaRepository<TipoDeRopa, Long> {

    List<TipoDeRopa> findAll();

    TipoDeRopa save(TipoDeRopa tipoDeRopa);

    void deleteById(Long id);

    Optional<TipoDeRopa> findById(Long id);

    boolean existsByNombre(String nombre);

    @Query("SELECT COUNT(t) > 0 FROM TipoDeRopa t WHERE t.nombre = :nombre AND t.id != :id")
    boolean existsTipoDeRopaByNombreAndNotId(@Param("nombre") String nombre, @Param("id") Long id);
}
