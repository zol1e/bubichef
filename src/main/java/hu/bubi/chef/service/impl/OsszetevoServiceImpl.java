package hu.bubi.chef.service.impl;

import hu.bubi.chef.service.OsszetevoService;
import hu.bubi.chef.domain.Osszetevo;
import hu.bubi.chef.repository.OsszetevoRepository;
import hu.bubi.chef.service.dto.OsszetevoDTO;
import hu.bubi.chef.service.mapper.OsszetevoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Osszetevo.
 */
@Service
@Transactional
public class OsszetevoServiceImpl implements OsszetevoService {

    private final Logger log = LoggerFactory.getLogger(OsszetevoServiceImpl.class);

    private final OsszetevoRepository osszetevoRepository;

    private final OsszetevoMapper osszetevoMapper;

    public OsszetevoServiceImpl(OsszetevoRepository osszetevoRepository, OsszetevoMapper osszetevoMapper) {
        this.osszetevoRepository = osszetevoRepository;
        this.osszetevoMapper = osszetevoMapper;
    }

    /**
     * Save a osszetevo.
     *
     * @param osszetevoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public OsszetevoDTO save(OsszetevoDTO osszetevoDTO) {
        log.debug("Request to save Osszetevo : {}", osszetevoDTO);

        Osszetevo osszetevo = osszetevoMapper.toEntity(osszetevoDTO);
        osszetevo = osszetevoRepository.save(osszetevo);
        return osszetevoMapper.toDto(osszetevo);
    }

    /**
     * Get all the osszetevos.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<OsszetevoDTO> findAll() {
        log.debug("Request to get all Osszetevos");
        return osszetevoRepository.findAll().stream()
            .map(osszetevoMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one osszetevo by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<OsszetevoDTO> findOne(Long id) {
        log.debug("Request to get Osszetevo : {}", id);
        return osszetevoRepository.findById(id)
            .map(osszetevoMapper::toDto);
    }

    /**
     * Delete the osszetevo by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Osszetevo : {}", id);
        osszetevoRepository.deleteById(id);
    }
}
