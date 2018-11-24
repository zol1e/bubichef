package hu.bubi.chef.service;

import hu.bubi.chef.service.dto.ReceptToOsszetevoDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing ReceptToOsszetevo.
 */
public interface ReceptToOsszetevoService {

    /**
     * Save a receptToOsszetevo.
     *
     * @param receptToOsszetevoDTO the entity to save
     * @return the persisted entity
     */
    ReceptToOsszetevoDTO save(ReceptToOsszetevoDTO receptToOsszetevoDTO);

    /**
     * Get all the receptToOsszetevos.
     *
     * @return the list of entities
     */
    List<ReceptToOsszetevoDTO> findAll();


    /**
     * Get the "id" receptToOsszetevo.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ReceptToOsszetevoDTO> findOne(Long id);

    /**
     * Delete the "id" receptToOsszetevo.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
