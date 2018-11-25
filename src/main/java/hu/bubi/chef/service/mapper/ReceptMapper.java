package hu.bubi.chef.service.mapper;

import hu.bubi.chef.domain.*;
import hu.bubi.chef.service.dto.ReceptDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Recept and its DTO ReceptDTO.
 */
@Mapper(componentModel = "spring", uses = {KategoriaMapper.class, HashTagMapper.class, ReceptToOsszetevoMapper.class})
public interface ReceptMapper extends EntityMapper<ReceptDTO, Recept> {

    @Mapping(source = "kategoria.id", target = "kategoriaId")
    ReceptDTO toDto(Recept recept);

    @Mapping(source = "kategoriaId", target = "kategoria")
    @Mapping(target = "osszetevoks", ignore = false)
    Recept toEntity(ReceptDTO receptDTO);

    default Recept fromId(Long id) {
        if (id == null) {
            return null;
        }
        Recept recept = new Recept();
        recept.setId(id);
        return recept;
    }
}
