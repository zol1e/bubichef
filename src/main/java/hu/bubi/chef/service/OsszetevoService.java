package hu.bubi.chef.service;

import hu.bubi.chef.service.dto.OsszetevoDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Osszetevo.
 */
public interface OsszetevoService {

    /**
     * Save a osszetevo.
     *
     * @param osszetevoDTO the entity to save
     * @return the persisted entity
     */
    OsszetevoDTO save(OsszetevoDTO osszetevoDTO);

    /**
     * Get all the osszetevos.
     *
     * @return the list of entities
     */
    List<OsszetevoDTO> findAll();


    /**
     * Get the "id" osszetevo.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<OsszetevoDTO> findOne(Long id);

    /**
     * Delete the "id" osszetevo.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
