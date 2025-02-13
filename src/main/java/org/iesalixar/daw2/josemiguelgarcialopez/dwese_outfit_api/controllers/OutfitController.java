package org.iesalixar.daw2.josemiguelgarcialopez.dwese_outfit_api.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.iesalixar.daw2.josemiguelgarcialopez.dwese_outfit_api.dtos.OutfitCreateDTO;
import org.iesalixar.daw2.josemiguelgarcialopez.dwese_outfit_api.dtos.OutfitDTO;
import org.iesalixar.daw2.josemiguelgarcialopez.dwese_outfit_api.dtos.TipoDeRopaDTO;
import org.iesalixar.daw2.josemiguelgarcialopez.dwese_outfit_api.services.OutfitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@RestController
@RequestMapping("/api/outfits")
public class OutfitController {

    private static final Logger logger = LoggerFactory.getLogger(OutfitController.class);
    private final OutfitService outfitService;

    public OutfitController(OutfitService outfitService) {
        this.outfitService = outfitService;
    }

    /**
     * Lista todos los outfits almacenados en la base de datos.
     *
     * @return ResponseEntity con la lista de outfits o un mensaje de error.
     */
    @Operation(summary = "Obtener todos los outfits", description = "Devuelve una lista de todas los outfits " + "disponibles en el sistema." )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Lista de outfits recuperada exitosamente",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = OutfitDTO.class))
                    )),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<Page<OutfitDTO>> getAllOutfits(
        @PageableDefault(size = 10, sort =  "nombre") Pageable pageable
    ) {
            logger.info("Solicitando todos los outfits con paginación: página {}, tamaño{}",
                    pageable.getPageNumber(), pageable.getPageSize());
        try {
            Page<OutfitDTO> outfits = outfitService.getAllOutfits(pageable);
            logger.info("Se han encontrado {} outfits.", outfits.getTotalElements());
            return ResponseEntity.ok(outfits);
        } catch (Exception e) {
            logger.error("Error al listar los outfits: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    /**
     * Obtiene un outfit específico por su ID.
     *
     * @param id ID del outfit solicitado.
     * @return ResponseEntity con el outfit encontrado o un mensaje de error.
     */

    @Operation(summary = "Obtener un outfit por ID", description = "Recupera un outfit " + "específico según su identificador único." )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Outfit encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = OutfitDTO.class))),
            @ApiResponse(responseCode = "404", description = "Outfit no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

    @GetMapping("/{id}")
    public ResponseEntity<?> getOutfitById(@PathVariable Long id) {
        logger.info("Buscando outfit con ID {}", id);
        try {
            Optional<OutfitDTO> outfitDTO = Optional.ofNullable(outfitService.getOutfitById(id));

            if (outfitDTO.isPresent()) {
                logger.info("Outfit con ID {} encontrado.", id);
                return ResponseEntity.ok(outfitDTO.get());
            } else {
                logger.warn("No se encontró ningún outfit con ID {}", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El outfit no existe.");
            }
        } catch (Exception e) {
            logger.error("Error al buscar el outfit con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al buscar el outfit.");
        }
    }

    /**
     * Crea un nuevo outfit.
     *
     * @param outfitCreateDTO DTO con los datos para crear el outfit.
     * @return ResponseEntity con el outfit creado o un mensaje de error.
     */
    @Operation(summary = "Crear un nuevo outfit", description = "Permite registar un nuevo outfit en la base de datos" )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Outfit creado exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = OutfitDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos proporcionados"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping
    public ResponseEntity<?> createOutfit(@Valid @RequestBody OutfitCreateDTO outfitCreateDTO, Locale locale) {
        logger.info("Creando un nuevo outfit...");
        try {
            OutfitDTO createdOutfit = outfitService.createOutfit(outfitCreateDTO, locale);
            logger.info("Outfit creado exitosamente con ID {}", createdOutfit.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(createdOutfit);
        } catch (IllegalArgumentException e) {
            logger.warn("Error al crear el outfit: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            logger.error("Error inesperado al crear el outfit: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear el outfit.");
        }
    }

    /**
     * Actualiza un outfit existente.
     *
     * @param id ID del outfit a actualizar.
     * @param outfitCreateDTO DTO con los datos para actualizar el outfit.
     * @return ResponseEntity con el outfit actualizado o un mensaje de error.
     */

    @Operation(summary = "Actualizar un outfit", description = "Permite actualizar los datos de un outfit existente" )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Outfit actualizado exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = OutfitDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> updateOutfit(@PathVariable Long id, @Valid @RequestBody OutfitCreateDTO outfitCreateDTO, Locale locale) {
        logger.info("Actualizando outfit con ID {}", id);
        try {
            OutfitDTO updatedOutfit = outfitService.updateOutfit(id, outfitCreateDTO, locale);
            logger.info("Outfit con ID {} actualizado exitosamente.", id);
            return ResponseEntity.ok(updatedOutfit);
        } catch (IllegalArgumentException e) {
            logger.warn("Error al actualizar el outfit con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            logger.error("Error inesperado al actualizar el outfit con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar el outfit.");
        }
    }

    /**
     * Elimina un outfit por su ID.
     * @param id ID del outfit a eliminar.
     * @return ResponseEntity con el resultado de la operación.
     */

    @Operation(summary = "Eliminar un outfit", description = "Permite eliminar un outfit específico de la base de datos" )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Outfit eliminado existosamente"),
            @ApiResponse(responseCode = "400", description = "Outfit no encotnrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOutfit(@PathVariable Long id) {
        logger.info("Eliminando outfit con ID {}", id);
        try {
            outfitService.deleteOutfit(id);
            logger.info("Outfit con ID {} eliminado exitosamente.", id);
            return ResponseEntity.ok("Outfit eliminado con éxito.");
        } catch (IllegalArgumentException e) {
            logger.warn("Error al eliminar el outfit con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            logger.error("Error inesperado al eliminar el outfit con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar el outfit.");
        }
    }
}
