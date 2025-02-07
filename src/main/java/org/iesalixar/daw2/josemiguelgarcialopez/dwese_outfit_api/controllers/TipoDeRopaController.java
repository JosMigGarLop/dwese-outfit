package org.iesalixar.daw2.josemiguelgarcialopez.dwese_outfit_api.controllers;

import jakarta.validation.Valid;
import org.iesalixar.daw2.josemiguelgarcialopez.dwese_outfit_api.dtos.TipoDeRopaCreateDTO;
import org.iesalixar.daw2.josemiguelgarcialopez.dwese_outfit_api.dtos.TipoDeRopaDTO;
import org.iesalixar.daw2.josemiguelgarcialopez.dwese_outfit_api.services.TipoDeRopaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/api/tiposderopa")
public class TipoDeRopaController {

    private static final Logger logger = LoggerFactory.getLogger(TipoDeRopaController.class);

    @Autowired
    private TipoDeRopaService tipoDeRopaService;

    /**
     * Obtiene todos los tipos de ropa disponibles.
     *
     * @return Lista de tipos de ropa o error en caso de fallo.
     */
    @GetMapping
    public ResponseEntity<List<TipoDeRopaDTO>> getAllTiposDeRopa() {
        logger.info("Solicitando la lista de todos los tipos de ropa...");
        try {
            List<TipoDeRopaDTO> tiposDeRopa = tipoDeRopaService.getAllTiposDeRopa();
            logger.info("Se han encontrado {} tipos de ropa.", tiposDeRopa.size());
            return ResponseEntity.ok(tiposDeRopa);
        } catch (Exception e) {
            logger.error("Error al listar los tipos de ropa: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Obtiene un tipo de ropa específico por su ID.
     *
     * @param id ID del tipo de ropa.
     * @return Tipo de ropa encontrado o mensaje de error si no existe.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getTipoDeRopaById(@PathVariable Long id) {
        logger.info("Buscando tipo de ropa con ID {}", id);
        try {
            TipoDeRopaDTO tipoDeRopa = tipoDeRopaService.getTipoDeRopaById(id);
            if (tipoDeRopa != null) {
                logger.info("Tipo de ropa con ID {} encontrado.", id);
                return ResponseEntity.ok(tipoDeRopa);
            } else {
                logger.warn("No se encontró tipo de ropa con ID {}", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tipo de ropa no encontrado.");
            }
        } catch (Exception e) {
            logger.error("Error al buscar el tipo de ropa con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al buscar el tipo de ropa.");
        }
    }

    /**
     * Crea un nuevo tipo de ropa.
     *
     * @param tipoDeRopaCreateDTO DTO con los datos del tipo de ropa a crear.
     * @param locale Idioma para los mensajes de error.
     * @return Tipo de ropa creado o mensaje de error.
     */
    @PostMapping
    public ResponseEntity<?> createTipoDeRopa(
            @Valid @RequestBody TipoDeRopaCreateDTO tipoDeRopaCreateDTO,
            Locale locale) {
        logger.info("Creando un nuevo tipo de ropa con nombre {}", tipoDeRopaCreateDTO.getNombre());
        try {
            TipoDeRopaDTO createdTipoDeRopa = tipoDeRopaService.createTipoDeRopa(tipoDeRopaCreateDTO, locale);
            logger.info("Tipo de ropa creado exitosamente con ID {}", createdTipoDeRopa.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(createdTipoDeRopa);
        } catch (IllegalArgumentException e) {
            logger.warn("Error al crear el tipo de ropa: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            logger.error("Error inesperado al crear el tipo de ropa: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear el tipo de ropa.");
        }
    }

    /**
     * Actualiza un tipo de ropa existente.
     *
     * @param id ID del tipo de ropa a actualizar.
     * @param tipoDeRopaDTO DTO con los datos del tipo de ropa a actualizar.
     * @param locale Idioma para los mensajes de error.
     * @return Tipo de ropa actualizado o mensaje de error.
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateTipoDeRopa(
            @PathVariable Long id,
            @Valid @RequestBody TipoDeRopaDTO tipoDeRopaDTO,
            Locale locale) {
        logger.info("Actualizando tipo de ropa con ID {}", id);
        try {
            TipoDeRopaDTO updatedTipoDeRopa = tipoDeRopaService.updateTipoDeRopa(id, tipoDeRopaDTO, locale);
            logger.info("Tipo de ropa con ID {} actualizado exitosamente.", id);
            return ResponseEntity.ok(updatedTipoDeRopa);
        } catch (IllegalArgumentException e) {
            logger.warn("Error al actualizar el tipo de ropa con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            logger.error("Error inesperado al actualizar el tipo de ropa con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar el tipo de ropa.");
        }
    }

    /**
     * Elimina un tipo de ropa por su ID.
     *
     * @param id ID del tipo de ropa a eliminar.
     * @return Mensaje de éxito o error en la eliminación.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTipoDeRopa(@PathVariable Long id) {
        logger.info("Eliminando tipo de ropa con ID {}", id);
        try {
            tipoDeRopaService.deleteTipoDeRopa(id);
            logger.info("Tipo de ropa con ID {} eliminado exitosamente.", id);
            return ResponseEntity.ok("Tipo de ropa eliminado con éxito.");
        } catch (IllegalArgumentException e) {
            logger.warn("Error al eliminar el tipo de ropa con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            logger.error("Error inesperado al eliminar el tipo de ropa con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar el tipo de ropa.");
        }
    }
}
