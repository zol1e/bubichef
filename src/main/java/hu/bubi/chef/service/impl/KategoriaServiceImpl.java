package hu.bubi.chef.service.impl;

import hu.bubi.chef.service.KategoriaService;
import hu.bubi.chef.domain.Kategoria;
import hu.bubi.chef.repository.KategoriaRepository;
import hu.bubi.chef.service.dto.KategoriaDTO;
import hu.bubi.chef.service.mapper.KategoriaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Kategoria.
 */
@Service
@Transactional
public class KategoriaServiceImpl implements KategoriaService {

    private final Logger log = LoggerFactory.getLogger(KategoriaServiceImpl.class);

    private final KategoriaRepository kategoriaRepository;

    private final KategoriaMapper kategoriaMapper;

    public KategoriaServiceImpl(KategoriaRepository kategoriaRepository, KategoriaMapper kategoriaMapper) {
        this.kategoriaRepository = kategoriaRepository;
        this.kategoriaMapper = kategoriaMapper;
    }

    /**
     * Save a kategoria.
     *
     * @param kategoriaDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public KategoriaDTO save(KategoriaDTO kategoriaDTO) {
        log.debug("Request to save Kategoria : {}", kategoriaDTO);

        Kategoria kategoria = kategoriaMapper.toEntity(kategoriaDTO);
        kategoria = kategoriaRepository.save(kategoria);
        return kategoriaMapper.toDto(kategoria);
    }

    /**
     * Get all the kategorias.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<KategoriaDTO> findAll() {
        log.debug("Request to get all Kategorias");
        return kategoriaRepository.findAll().stream()
            .map(kategoriaMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one kategoria by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<KategoriaDTO> findOne(Long id) {
        log.debug("Request to get Kategoria : {}", id);
        return kategoriaRepository.findById(id)
            .map(kategoriaMapper::toDto);
    }

    /**
     * Delete the kategoria by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Kategoria : {}", id);
        kategoriaRepository.deleteById(id);
    }
}
