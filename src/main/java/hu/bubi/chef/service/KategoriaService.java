package hu.bubi.chef.service;

import hu.bubi.chef.service.dto.KategoriaDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Kategoria.
 */
public interface KategoriaService {

    /**
     * Save a kategoria.
     *
     * @param kategoriaDTO the entity to save
     * @return the persisted entity
     */
    KategoriaDTO save(KategoriaDTO kategoriaDTO);

    /**
     * Get all the kategorias.
     *
     * @return the list of entities
     */
    List<KategoriaDTO> findAll();


    /**
     * Get the "id" kategoria.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<KategoriaDTO> findOne(Long id);

    /**
     * Delete the "id" kategoria.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
