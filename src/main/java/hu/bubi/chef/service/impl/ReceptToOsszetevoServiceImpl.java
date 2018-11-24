package hu.bubi.chef.service.impl;

import hu.bubi.chef.service.ReceptToOsszetevoService;
import hu.bubi.chef.domain.ReceptToOsszetevo;
import hu.bubi.chef.repository.ReceptToOsszetevoRepository;
import hu.bubi.chef.service.dto.ReceptToOsszetevoDTO;
import hu.bubi.chef.service.mapper.ReceptToOsszetevoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing ReceptToOsszetevo.
 */
@Service
@Transactional
public class ReceptToOsszetevoServiceImpl implements ReceptToOsszetevoService {

    private final Logger log = LoggerFactory.getLogger(ReceptToOsszetevoServiceImpl.class);

    private final ReceptToOsszetevoRepository receptToOsszetevoRepository;

    private final ReceptToOsszetevoMapper receptToOsszetevoMapper;

    public ReceptToOsszetevoServiceImpl(ReceptToOsszetevoRepository receptToOsszetevoRepository, ReceptToOsszetevoMapper receptToOsszetevoMapper) {
        this.receptToOsszetevoRepository = receptToOsszetevoRepository;
        this.receptToOsszetevoMapper = receptToOsszetevoMapper;
    }

    /**
     * Save a receptToOsszetevo.
     *
     * @param receptToOsszetevoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ReceptToOsszetevoDTO save(ReceptToOsszetevoDTO receptToOsszetevoDTO) {
        log.debug("Request to save ReceptToOsszetevo : {}", receptToOsszetevoDTO);

        ReceptToOsszetevo receptToOsszetevo = receptToOsszetevoMapper.toEntity(receptToOsszetevoDTO);
        receptToOsszetevo = receptToOsszetevoRepository.save(receptToOsszetevo);
        return receptToOsszetevoMapper.toDto(receptToOsszetevo);
    }

    /**
     * Get all the receptToOsszetevos.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ReceptToOsszetevoDTO> findAll() {
        log.debug("Request to get all ReceptToOsszetevos");
        return receptToOsszetevoRepository.findAll().stream()
            .map(receptToOsszetevoMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one receptToOsszetevo by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ReceptToOsszetevoDTO> findOne(Long id) {
        log.debug("Request to get ReceptToOsszetevo : {}", id);
        return receptToOsszetevoRepository.findById(id)
            .map(receptToOsszetevoMapper::toDto);
    }

    /**
     * Delete the receptToOsszetevo by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ReceptToOsszetevo : {}", id);
        receptToOsszetevoRepository.deleteById(id);
    }
}
