package hu.bubi.chef.service.impl;

import hu.bubi.chef.service.ReceptService;
import hu.bubi.chef.domain.Recept;
import hu.bubi.chef.repository.ReceptRepository;
import hu.bubi.chef.service.dto.ReceptDTO;
import hu.bubi.chef.service.mapper.ReceptMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Recept.
 */
@Service
@Transactional
public class ReceptServiceImpl implements ReceptService {

    private final Logger log = LoggerFactory.getLogger(ReceptServiceImpl.class);

    private final ReceptRepository receptRepository;

    private final ReceptMapper receptMapper;

    public ReceptServiceImpl(ReceptRepository receptRepository, ReceptMapper receptMapper) {
        this.receptRepository = receptRepository;
        this.receptMapper = receptMapper;
    }

    /**
     * Save a recept.
     *
     * @param receptDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ReceptDTO save(ReceptDTO receptDTO) {
        log.debug("Request to save Recept : {}", receptDTO);

        Recept recept = receptMapper.toEntity(receptDTO);
        recept = receptRepository.save(recept);
        return receptMapper.toDto(recept);
    }

    /**
     * Get all the recepts.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ReceptDTO> findAll() {
        log.debug("Request to get all Recepts");
        return receptRepository.findAllWithEagerRelationships().stream()
            .map(receptMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all the Recept with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<ReceptDTO> findAllWithEagerRelationships(Pageable pageable) {
        return receptRepository.findAllWithEagerRelationships(pageable).map(receptMapper::toDto);
    }
    

    /**
     * Get one recept by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ReceptDTO> findOne(Long id) {
        log.debug("Request to get Recept : {}", id);
        
        Optional<Recept> findById = receptRepository.findById(id);
        Optional<ReceptDTO> map = findById.map(receptMapper::toDto);
        map.get().setKategoriaNev(findById.get().getKategoria().getNev());
        
        return map;
         
        //return receptRepository.findOneWithEagerRelationships(id)
        //    .map(receptMapper::toDto);
    }

    /**
     * Delete the recept by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Recept : {}", id);
        receptRepository.deleteById(id);
    }
}
