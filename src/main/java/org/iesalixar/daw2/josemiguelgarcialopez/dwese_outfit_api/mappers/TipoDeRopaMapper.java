package org.iesalixar.daw2.josemiguelgarcialopez.dwese_outfit_api.mappers;

import org.iesalixar.daw2.josemiguelgarcialopez.dwese_outfit_api.dtos.TipoDeRopaCreateDTO;
import org.iesalixar.daw2.josemiguelgarcialopez.dwese_outfit_api.dtos.TipoDeRopaDTO;
import org.iesalixar.daw2.josemiguelgarcialopez.dwese_outfit_api.entities.TipoDeRopa;
import org.springframework.stereotype.Component;

@Component
public class TipoDeRopaMapper {

    /**
     * Convierte una entidad `TipoDeRopa` a un `TipoDeRopaDTO`.
     *
     * @param tipoDeRopa Entidad de tipo de ropa.
     * @return DTO correspondiente o `null` si `tipoDeRopa` es `null`.
     */
    public TipoDeRopaDTO toDTO(TipoDeRopa tipoDeRopa) {
        if (tipoDeRopa == null) {
            return null;
        }

        return new TipoDeRopaDTO(
                tipoDeRopa.getId(),
                tipoDeRopa.getNombre(),
                tipoDeRopa.getDescripcion()
        );
    }

    /**
     * Convierte un `TipoDeRopaDTO` a una entidad `TipoDeRopa`.
     *
     * @param dto DTO de tipo de ropa.
     * @return Entidad `TipoDeRopa` o `null` si `dto` es `null`.
     */
    public TipoDeRopa toEntity(TipoDeRopaDTO dto) {
        if (dto == null) {
            return null;
        }

        return new TipoDeRopa(
                dto.getId(),
                dto.getNombre(),
                dto.getDescripcion()
        );
    }

    /**
     * Convierte un `TipoDeRopaCreateDTO` a una entidad `TipoDeRopa` (para creación).
     *
     * @param createDTO DTO para crear tipos de ropa.
     * @return Entidad `TipoDeRopa` o `null` si `createDTO` es `null`.
     */
    public TipoDeRopa toEntity(TipoDeRopaCreateDTO createDTO) {
        if (createDTO == null) {
            return null;
        }

        TipoDeRopa tipoDeRopa = new TipoDeRopa();
        tipoDeRopa.setNombre(createDTO.getNombre());
        tipoDeRopa.setDescripcion(createDTO.getDescripcion());

        return tipoDeRopa;
    }
}
