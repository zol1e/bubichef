package hu.bubi.chef.service.mapper;

import hu.bubi.chef.domain.*;
import hu.bubi.chef.service.dto.ReceptToOsszetevoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ReceptToOsszetevo and its DTO ReceptToOsszetevoDTO.
 */
@Mapper(componentModel = "spring", uses = {ReceptMapper.class, OsszetevoMapper.class})
public interface ReceptToOsszetevoMapper extends EntityMapper<ReceptToOsszetevoDTO, ReceptToOsszetevo> {

    @Mapping(source = "recept.id", target = "receptId")
    @Mapping(source = "osszetevo.id", target = "osszetevoId")
    ReceptToOsszetevoDTO toDto(ReceptToOsszetevo receptToOsszetevo);

    @Mapping(source = "receptId", target = "recept")
    @Mapping(source = "osszetevoId", target = "osszetevo")
    ReceptToOsszetevo toEntity(ReceptToOsszetevoDTO receptToOsszetevoDTO);

    default ReceptToOsszetevo fromId(Long id) {
        if (id == null) {
            return null;
        }
        ReceptToOsszetevo receptToOsszetevo = new ReceptToOsszetevo();
        receptToOsszetevo.setId(id);
        return receptToOsszetevo;
    }
}
