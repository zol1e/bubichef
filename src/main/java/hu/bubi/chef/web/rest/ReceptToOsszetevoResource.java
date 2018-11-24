package hu.bubi.chef.web.rest;

import com.codahale.metrics.annotation.Timed;
import hu.bubi.chef.service.ReceptToOsszetevoService;
import hu.bubi.chef.web.rest.errors.BadRequestAlertException;
import hu.bubi.chef.web.rest.util.HeaderUtil;
import hu.bubi.chef.service.dto.ReceptToOsszetevoDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ReceptToOsszetevo.
 */
@RestController
@RequestMapping("/api")
public class ReceptToOsszetevoResource {

    private final Logger log = LoggerFactory.getLogger(ReceptToOsszetevoResource.class);

    private static final String ENTITY_NAME = "receptToOsszetevo";

    private final ReceptToOsszetevoService receptToOsszetevoService;

    public ReceptToOsszetevoResource(ReceptToOsszetevoService receptToOsszetevoService) {
        this.receptToOsszetevoService = receptToOsszetevoService;
    }

    /**
     * POST  /recept-to-osszetevos : Create a new receptToOsszetevo.
     *
     * @param receptToOsszetevoDTO the receptToOsszetevoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new receptToOsszetevoDTO, or with status 400 (Bad Request) if the receptToOsszetevo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/recept-to-osszetevos")
    @Timed
    public ResponseEntity<ReceptToOsszetevoDTO> createReceptToOsszetevo(@Valid @RequestBody ReceptToOsszetevoDTO receptToOsszetevoDTO) throws URISyntaxException {
        log.debug("REST request to save ReceptToOsszetevo : {}", receptToOsszetevoDTO);
        if (receptToOsszetevoDTO.getId() != null) {
            throw new BadRequestAlertException("A new receptToOsszetevo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ReceptToOsszetevoDTO result = receptToOsszetevoService.save(receptToOsszetevoDTO);
        return ResponseEntity.created(new URI("/api/recept-to-osszetevos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /recept-to-osszetevos : Updates an existing receptToOsszetevo.
     *
     * @param receptToOsszetevoDTO the receptToOsszetevoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated receptToOsszetevoDTO,
     * or with status 400 (Bad Request) if the receptToOsszetevoDTO is not valid,
     * or with status 500 (Internal Server Error) if the receptToOsszetevoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/recept-to-osszetevos")
    @Timed
    public ResponseEntity<ReceptToOsszetevoDTO> updateReceptToOsszetevo(@Valid @RequestBody ReceptToOsszetevoDTO receptToOsszetevoDTO) throws URISyntaxException {
        log.debug("REST request to update ReceptToOsszetevo : {}", receptToOsszetevoDTO);
        if (receptToOsszetevoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ReceptToOsszetevoDTO result = receptToOsszetevoService.save(receptToOsszetevoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, receptToOsszetevoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /recept-to-osszetevos : get all the receptToOsszetevos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of receptToOsszetevos in body
     */
    @GetMapping("/recept-to-osszetevos")
    @Timed
    public List<ReceptToOsszetevoDTO> getAllReceptToOsszetevos() {
        log.debug("REST request to get all ReceptToOsszetevos");
        return receptToOsszetevoService.findAll();
    }

    /**
     * GET  /recept-to-osszetevos/:id : get the "id" receptToOsszetevo.
     *
     * @param id the id of the receptToOsszetevoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the receptToOsszetevoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/recept-to-osszetevos/{id}")
    @Timed
    public ResponseEntity<ReceptToOsszetevoDTO> getReceptToOsszetevo(@PathVariable Long id) {
        log.debug("REST request to get ReceptToOsszetevo : {}", id);
        Optional<ReceptToOsszetevoDTO> receptToOsszetevoDTO = receptToOsszetevoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(receptToOsszetevoDTO);
    }

    /**
     * DELETE  /recept-to-osszetevos/:id : delete the "id" receptToOsszetevo.
     *
     * @param id the id of the receptToOsszetevoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/recept-to-osszetevos/{id}")
    @Timed
    public ResponseEntity<Void> deleteReceptToOsszetevo(@PathVariable Long id) {
        log.debug("REST request to delete ReceptToOsszetevo : {}", id);
        receptToOsszetevoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
