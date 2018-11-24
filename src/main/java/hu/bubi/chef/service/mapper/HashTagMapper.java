package hu.bubi.chef.service.mapper;

import hu.bubi.chef.domain.*;
import hu.bubi.chef.service.dto.HashTagDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity HashTag and its DTO HashTagDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface HashTagMapper extends EntityMapper<HashTagDTO, HashTag> {


    @Mapping(target = "recepteks", ignore = true)
    HashTag toEntity(HashTagDTO hashTagDTO);

    default HashTag fromId(Long id) {
        if (id == null) {
            return null;
        }
        HashTag hashTag = new HashTag();
        hashTag.setId(id);
        return hashTag;
    }
}
