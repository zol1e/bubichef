package hu.bubi.chef.service.mapper;

import hu.bubi.chef.domain.*;
import hu.bubi.chef.service.dto.OsszetevoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Osszetevo and its DTO OsszetevoDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface OsszetevoMapper extends EntityMapper<OsszetevoDTO, Osszetevo> {


    @Mapping(target = "recepteks", ignore = true)
    Osszetevo toEntity(OsszetevoDTO osszetevoDTO);

    default Osszetevo fromId(Long id) {
        if (id == null) {
            return null;
        }
        Osszetevo osszetevo = new Osszetevo();
        osszetevo.setId(id);
        return osszetevo;
    }
}
