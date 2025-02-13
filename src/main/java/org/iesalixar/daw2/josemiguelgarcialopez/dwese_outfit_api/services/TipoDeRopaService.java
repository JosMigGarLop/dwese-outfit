package org.iesalixar.daw2.josemiguelgarcialopez.dwese_outfit_api.services;

import jakarta.validation.Valid;

import org.iesalixar.daw2.josemiguelgarcialopez.dwese_outfit_api.dtos.TipoDeRopaCreateDTO;
import org.iesalixar.daw2.josemiguelgarcialopez.dwese_outfit_api.dtos.TipoDeRopaDTO;
import org.iesalixar.daw2.josemiguelgarcialopez.dwese_outfit_api.entities.TipoDeRopa;
import org.iesalixar.daw2.josemiguelgarcialopez.dwese_outfit_api.mappers.TipoDeRopaMapper;
import org.iesalixar.daw2.josemiguelgarcialopez.dwese_outfit_api.repositories.TipoDeRopaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Locale;


@Service
public class TipoDeRopaService {

    private static final Logger logger = LoggerFactory.getLogger(TipoDeRopaService.class);

    @Autowired
    private TipoDeRopaRepository tipoDeRopaRepository;

    @Autowired
    private TipoDeRopaMapper tipoDeRopaMapper;

    @Autowired
    private MessageSource messageSource;

    /**
     * Obtiene todos los tipos de ropa y los convierte en una lista de TipoDeRopaDTO.
     *
     * @return Lista de TipoDeRopaDTO.
     */
    public Page<TipoDeRopaDTO> getAllTiposDeRopa(Pageable pageable) {
        logger.info("Solicitando todos los tipos de ropa con paginación: página {}, tamaño{}",
                pageable.getPageNumber(), pageable.getPageSize());
        try {
            Page<TipoDeRopa> tiposDeRopa = tipoDeRopaRepository.findAll(pageable);
            logger.info("Se han encontrado {} tipos de ropa en la página actual.", tiposDeRopa.getNumberOfElements());
            return tiposDeRopa.map(tipoDeRopaMapper::toDTO);
        } catch (Exception e) {
            logger.error("Error al obtener la lista de tipos de ropa: {}", e.getMessage());
            throw e;
        }
    }


    /**
     * Obtiene un tipo de ropa por su ID y lo convierte en un TipoDeRopaDTO.
     *
     * @param id Identificador único del tipo de ropa.
     * @return TipoDeRopaDTO encontrado.
     * @throws IllegalArgumentException Si no se encuentra el tipo de ropa.
     */
    public TipoDeRopaDTO getTipoDeRopaById(Long id) {
        logger.info("Buscando tipo de ropa con ID {}", id);
        TipoDeRopa tipoDeRopa = tipoDeRopaRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("No se encontró el tipo de ropa con ID {}", id);
                    return new IllegalArgumentException("El tipo de ropa no existe.");
                });

        logger.info("Tipo de ropa con ID {} encontrado.", id);
        return tipoDeRopaMapper.toDTO(tipoDeRopa);
    }

    /**
     * Crea un nuevo tipo de ropa en la base de datos.
     *
     * @param tipoDeRopaCreateDTO DTO con los datos del tipo de ropa a crear.
     * @param locale Idioma para los mensajes de error.
     * @return DTO del tipo de ropa creado.
     * @throws IllegalArgumentException Si ya existe un tipo de ropa con el mismo nombre.
     */
    public TipoDeRopaDTO createTipoDeRopa(@Valid TipoDeRopaCreateDTO tipoDeRopaCreateDTO, Locale locale) {
        logger.info("Creando un nuevo tipo de ropa con nombre {}", tipoDeRopaCreateDTO.getNombre());

        if (tipoDeRopaRepository.existsByNombre(tipoDeRopaCreateDTO.getNombre())) {
            String errorMessage = messageSource.getMessage("msg.tipoDeRopa.insert.nameExist", null, locale);
            logger.warn("Error al crear tipo de ropa: {}", errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }

        TipoDeRopa tipoDeRopa = tipoDeRopaMapper.toEntity(tipoDeRopaCreateDTO);
        TipoDeRopa savedTipoDeRopa = tipoDeRopaRepository.save(tipoDeRopa);
        logger.info("Tipo de ropa creado exitosamente con ID {}", savedTipoDeRopa.getId());

        return tipoDeRopaMapper.toDTO(savedTipoDeRopa);
    }

    /**
     * Actualiza un tipo de ropa existente en la base de datos.
     *
     * @param id Identificador del tipo de ropa a actualizar.
     * @param tipoDeRopaDTO DTO con los nuevos datos del tipo de ropa.
     * @param locale Idioma para los mensajes de error.
     * @return DTO del tipo de ropa actualizado.
     * @throws IllegalArgumentException Si el tipo de ropa no existe o si el nombre ya está en uso.
     */
    public TipoDeRopaDTO updateTipoDeRopa(Long id, @Valid TipoDeRopaDTO tipoDeRopaDTO, Locale locale) {
        logger.info("Actualizando tipo de ropa con ID {}", id);
        TipoDeRopa existingTipoDeRopa = tipoDeRopaRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("No se encontró el tipo de ropa con ID {}", id);
                    return new IllegalArgumentException("El tipo de ropa no existe.");
                });

        if (tipoDeRopaRepository.existsTipoDeRopaByNombreAndNotId(tipoDeRopaDTO.getNombre(), id)) {
            String errorMessage = messageSource.getMessage("msg.tipoDeRopa.update.nameExist", null, locale);
            logger.warn("Error al actualizar tipo de ropa: {}", errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }

        existingTipoDeRopa.setNombre(tipoDeRopaDTO.getNombre());
        existingTipoDeRopa.setDescripcion(tipoDeRopaDTO.getDescripcion());

        TipoDeRopa updatedTipoDeRopa = tipoDeRopaRepository.save(existingTipoDeRopa);
        logger.info("Tipo de ropa con ID {} actualizado exitosamente.", id);

        return tipoDeRopaMapper.toDTO(updatedTipoDeRopa);
    }

    /**
     * Elimina un tipo de ropa específico por su ID.
     *
     * @param id Identificador único del tipo de ropa.
     * @throws IllegalArgumentException Si el tipo de ropa no existe.
     */
    public void deleteTipoDeRopa(Long id) {
        logger.info("Buscando tipo de ropa con ID {}", id);
        TipoDeRopa tipoDeRopa = tipoDeRopaRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("No se encontró el tipo de ropa con ID {}", id);
                    return new IllegalArgumentException("El tipo de ropa no existe.");
                });

        tipoDeRopaRepository.deleteById(id);
        logger.info("Tipo de ropa con ID {} eliminado exitosamente.", id);
    }
}
