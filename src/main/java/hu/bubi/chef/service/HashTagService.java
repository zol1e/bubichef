package hu.bubi.chef.service;

import hu.bubi.chef.service.dto.HashTagDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing HashTag.
 */
public interface HashTagService {

    /**
     * Save a hashTag.
     *
     * @param hashTagDTO the entity to save
     * @return the persisted entity
     */
    HashTagDTO save(HashTagDTO hashTagDTO);

    /**
     * Get all the hashTags.
     *
     * @return the list of entities
     */
    List<HashTagDTO> findAll();


    /**
     * Get the "id" hashTag.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<HashTagDTO> findOne(Long id);

    /**
     * Delete the "id" hashTag.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
