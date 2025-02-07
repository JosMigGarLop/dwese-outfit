package org.iesalixar.daw2.josemiguelgarcialopez.dwese_outfit_api.mappers;

import org.iesalixar.daw2.josemiguelgarcialopez.dwese_outfit_api.dtos.OutfitCreateDTO;
import org.iesalixar.daw2.josemiguelgarcialopez.dwese_outfit_api.dtos.OutfitDTO;
import org.iesalixar.daw2.josemiguelgarcialopez.dwese_outfit_api.entities.Outfit;
import org.iesalixar.daw2.josemiguelgarcialopez.dwese_outfit_api.entities.TipoDeRopa;
import org.springframework.stereotype.Component;

@Component
public class OutfitMapper {

    /**
     * Convierte una entidad `Outfit` a un `OutfitDTO`.
     *
     * @param outfit Entidad de outfit.
     * @return DTO correspondiente o `null` si el outfit es `null`.
     */
    public OutfitDTO toDTO(Outfit outfit) {
        if (outfit == null) {
            return null;
        }

        String tipoDeRopaNombre = (outfit.getTipoDeRopa() != null) ? outfit.getTipoDeRopa().getNombre() : null;

        return new OutfitDTO(
                outfit.getId(),
                outfit.getNombre(),
                outfit.getDescripcion(),
                outfit.getFechaCreacion(),
                tipoDeRopaNombre
        );
    }

    /**
     * Convierte un `OutfitCreateDTO` a una entidad `Outfit` (para creación).
     *
     * @param createDTO DTO para crear outfits.
     * @param tipoDeRopa Entidad `TipoDeRopa` asociada (puede ser `null`).
     * @return Entidad `Outfit` o `null` si `createDTO` es `null`.
     */
    public Outfit toEntity(OutfitCreateDTO createDTO, TipoDeRopa tipoDeRopa) {
        if (createDTO == null) {
            return null;
        }

        return new Outfit(
                null,
                createDTO.getNombre(),
                createDTO.getDescripcion(),
                createDTO.getFechaCreacion(),
                tipoDeRopa
        );
    }
}
