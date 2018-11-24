package hu.bubi.chef.service;

import hu.bubi.chef.service.dto.ReceptDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Recept.
 */
public interface ReceptService {

    /**
     * Save a recept.
     *
     * @param receptDTO the entity to save
     * @return the persisted entity
     */
    ReceptDTO save(ReceptDTO receptDTO);

    /**
     * Get all the recepts.
     *
     * @return the list of entities
     */
    List<ReceptDTO> findAll();

    /**
     * Get all the Recept with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    Page<ReceptDTO> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" recept.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ReceptDTO> findOne(Long id);

    /**
     * Delete the "id" recept.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
