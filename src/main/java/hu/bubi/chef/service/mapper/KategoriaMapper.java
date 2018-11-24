package hu.bubi.chef.service.mapper;

import hu.bubi.chef.domain.*;
import hu.bubi.chef.service.dto.KategoriaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Kategoria and its DTO KategoriaDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface KategoriaMapper extends EntityMapper<KategoriaDTO, Kategoria> {



    default Kategoria fromId(Long id) {
        if (id == null) {
            return null;
        }
        Kategoria kategoria = new Kategoria();
        kategoria.setId(id);
        return kategoria;
    }
}
