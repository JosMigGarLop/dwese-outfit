package org.iesalixar.daw2.josemiguelgarcialopez.dwese_outfit_api.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.iesalixar.daw2.josemiguelgarcialopez.dwese_outfit_api.dtos.OutfitDTO;
import org.iesalixar.daw2.josemiguelgarcialopez.dwese_outfit_api.dtos.TipoDeRopaCreateDTO;
import org.iesalixar.daw2.josemiguelgarcialopez.dwese_outfit_api.dtos.TipoDeRopaDTO;
import org.iesalixar.daw2.josemiguelgarcialopez.dwese_outfit_api.services.TipoDeRopaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    @Operation(summary = "Obtener todos los tipos de topa", description = "Devuelve una lista de todoss los tipos de ropa " + "disponibles en el sistema." )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Lista de tipos de ropa recuperada exitosamente",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = OutfitDTO.class))
                    )),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<Page<TipoDeRopaDTO>> getAllTiposDeRopa(
            @PageableDefault(size = 10, sort =  "nombre") Pageable pageable
    ) {
        logger.info("Solicitando todos los tipos de ropa con paginación: página {}, tamaño{}",
                pageable.getPageNumber(), pageable.getPageSize());
        try {
            Page<TipoDeRopaDTO> tiposDeRopa = tipoDeRopaService.getAllTiposDeRopa(pageable);
            logger.info("Se han encontrado {} tipos de ropa.", tiposDeRopa.getTotalElements());
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
    @Operation(summary = "Obtener un tipo de ropa por ID", description = "Recupera un tipo de ropa " + "específico según su identificador único." )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tipo de ropa encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = OutfitDTO.class))),
            @ApiResponse(responseCode = "404", description = "Tipo de ropa no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
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
    @Operation(summary = "Crear un nuevo tipo de ropa", description = "Permite registar un nuevo tipo de ropa en la base de datos" )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tipo de ropa creado exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = OutfitDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos proporcionados"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
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
    @Operation(summary = "Actualizar un tipo de ropa", description = "Permite actualizar los datos de un tipo de ropa existente" )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tipo de ropa actualizado exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = OutfitDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
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
    @Operation(summary = "Eliminar un tipo de ropa", description = "Permite eliminar un tipo de ropa específico de la base de datos" )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tipo de ropa eliminado existosamente"),
            @ApiResponse(responseCode = "400", description = "Tipo de ropa no encotnrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
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
