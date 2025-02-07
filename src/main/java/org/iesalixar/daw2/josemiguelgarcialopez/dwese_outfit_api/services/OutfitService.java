package org.iesalixar.daw2.josemiguelgarcialopez.dwese_outfit_api.services;

import jakarta.validation.Valid;
import org.iesalixar.daw2.josemiguelgarcialopez.dwese_outfit_api.dtos.OutfitCreateDTO;
import org.iesalixar.daw2.josemiguelgarcialopez.dwese_outfit_api.dtos.OutfitDTO;
import org.iesalixar.daw2.josemiguelgarcialopez.dwese_outfit_api.entities.Outfit;
import org.iesalixar.daw2.josemiguelgarcialopez.dwese_outfit_api.entities.TipoDeRopa;
import org.iesalixar.daw2.josemiguelgarcialopez.dwese_outfit_api.mappers.OutfitMapper;
import org.iesalixar.daw2.josemiguelgarcialopez.dwese_outfit_api.repositories.OutfitRepository;
import org.iesalixar.daw2.josemiguelgarcialopez.dwese_outfit_api.repositories.TipoDeRopaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OutfitService {

    private static final Logger logger = LoggerFactory.getLogger(OutfitService.class);

    @Autowired
    private OutfitRepository outfitRepository;

    @Autowired
    private TipoDeRopaRepository tipoDeRopaRepository;

    @Autowired
    private OutfitMapper outfitMapper;

    @Autowired
    private MessageSource messageSource;

    /**
     * Obtiene todos los outfits y los convierte en una lista de OutfitDTO.
     *
     * @return Lista de OutfitDTO.
     */
    public List<OutfitDTO> getAllOutfits() {
        logger.info("Solicitando todos los outfits...");
        try {
            List<Outfit> outfits = outfitRepository.findAll();
            logger.info("Se han encontrado {} outfits.", outfits.size());
            return outfits.stream()
                    .map(outfitMapper::toDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error al obtener la lista de outfits: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * Obtiene un outfit por su ID y lo convierte en un OutfitDTO.
     *
     * @param id Identificador único del outfit.
     * @return OutfitDTO del outfit encontrado.
     */
    public OutfitDTO getOutfitById(Long id) {
        logger.info("Buscando outfit con ID {}", id);
        Outfit outfit = outfitRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("No se encontró el outfit con ID {}", id);
                    return new IllegalArgumentException("El outfit no existe.");
                });
        logger.info("Outfit con ID {} encontrado.", id);
        return outfitMapper.toDTO(outfit);
    }

    /**
     * Crea un nuevo outfit en la base de datos.
     *
     * @param outfitCreateDTO DTO con los datos del outfit a crear.
     * @param locale Idioma para los mensajes de error.
     * @return DTO del outfit creado.
     */
    public OutfitDTO createOutfit(@Valid OutfitCreateDTO outfitCreateDTO, Locale locale) {
        logger.info("Creando un nuevo outfit con nombre {}", outfitCreateDTO.getNombre());

        TipoDeRopa tipoDeRopa = null;
        if (outfitCreateDTO.getTipoDeRopaId() != null) {
            tipoDeRopa = tipoDeRopaRepository.findById(outfitCreateDTO.getTipoDeRopaId())
                    .orElseThrow(() -> {
                        String errorMessage = messageSource.getMessage("msg.outfit-service.tipoRopaNotFound", null, locale);
                        logger.warn("Error al crear outfit: {}", errorMessage);
                        return new IllegalArgumentException(errorMessage);
                    });
        }

        Outfit outfit = outfitMapper.toEntity(outfitCreateDTO, tipoDeRopa);
        Outfit savedOutfit = outfitRepository.save(outfit);
        logger.info("Outfit creado exitosamente con ID {}", savedOutfit.getId());
        return outfitMapper.toDTO(savedOutfit);
    }

    /**
     * Actualiza un outfit existente en la base de datos.
     *
     * @param id Identificador del outfit a actualizar.
     * @param outfitCreateDTO DTO con los nuevos datos del outfit.
     * @param locale Idioma para los mensajes de error.
     * @return DTO del outfit actualizado.
     */
    public OutfitDTO updateOutfit(Long id, @Valid OutfitCreateDTO outfitCreateDTO, Locale locale) {
        logger.info("Actualizando outfit con ID {}", id);
        Outfit existingOutfit = outfitRepository.findById(id)
                .orElseThrow(() -> {
                    String errorMessage = messageSource.getMessage("msg.outfit-service.outfitNotFound", null, locale);
                    logger.warn("Error al actualizar outfit: {}", errorMessage);
                    return new IllegalArgumentException(errorMessage);
                });

        TipoDeRopa tipoDeRopa = null;
        if (outfitCreateDTO.getTipoDeRopaId() != null) {
            tipoDeRopa = tipoDeRopaRepository.findById(outfitCreateDTO.getTipoDeRopaId())
                    .orElseThrow(() -> {
                        String errorMessage = messageSource.getMessage("msg.outfit-service.tipoRopaNotFound", null, locale);
                        logger.warn("Error al actualizar outfit: {}", errorMessage);
                        return new IllegalArgumentException(errorMessage);
                    });
        }

        existingOutfit.setNombre(outfitCreateDTO.getNombre());
        existingOutfit.setDescripcion(outfitCreateDTO.getDescripcion());
        existingOutfit.setFechaCreacion(outfitCreateDTO.getFechaCreacion());
        existingOutfit.setTipoDeRopa(tipoDeRopa);

        Outfit updatedOutfit = outfitRepository.save(existingOutfit);
        logger.info("Outfit con ID {} actualizado exitosamente.", id);
        return outfitMapper.toDTO(updatedOutfit);
    }

    /**
     * Elimina un outfit específico por su ID.
     *
     * @param id Identificador único del outfit.
     */
    public void deleteOutfit(Long id) {
        logger.info("Buscando outfit con ID {}", id);
        Outfit outfit = outfitRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("No se encontró el outfit con ID {}", id);
                    return new IllegalArgumentException("El outfit no existe.");
                });

        outfitRepository.deleteById(id);
        logger.info("Outfit con ID {} eliminado exitosamente.", id);
    }
}
