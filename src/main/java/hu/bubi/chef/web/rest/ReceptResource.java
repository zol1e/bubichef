package hu.bubi.chef.web.rest;

import com.codahale.metrics.annotation.Timed;
import hu.bubi.chef.service.ReceptService;
import hu.bubi.chef.web.rest.errors.BadRequestAlertException;
import hu.bubi.chef.web.rest.util.HeaderUtil;
import hu.bubi.chef.service.dto.ReceptDTO;
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
 * REST controller for managing Recept.
 */
@RestController
@RequestMapping("/api")
public class ReceptResource {

    private final Logger log = LoggerFactory.getLogger(ReceptResource.class);

    private static final String ENTITY_NAME = "recept";

    private final ReceptService receptService;

    public ReceptResource(ReceptService receptService) {
        this.receptService = receptService;
    }

    /**
     * POST  /recepts : Create a new recept.
     *
     * @param receptDTO the receptDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new receptDTO, or with status 400 (Bad Request) if the recept has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/recepts")
    @Timed
    public ResponseEntity<ReceptDTO> createRecept(@Valid @RequestBody ReceptDTO receptDTO) throws URISyntaxException {
        log.debug("REST request to save Recept : {}", receptDTO);
        if (receptDTO.getId() != null) {
            throw new BadRequestAlertException("A new recept cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ReceptDTO result = receptService.save(receptDTO);
        return ResponseEntity.created(new URI("/api/recepts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /recepts : Updates an existing recept.
     *
     * @param receptDTO the receptDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated receptDTO,
     * or with status 400 (Bad Request) if the receptDTO is not valid,
     * or with status 500 (Internal Server Error) if the receptDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/recepts")
    @Timed
    public ResponseEntity<ReceptDTO> updateRecept(@Valid @RequestBody ReceptDTO receptDTO) throws URISyntaxException {
        log.debug("REST request to update Recept : {}", receptDTO);
        if (receptDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ReceptDTO result = receptService.save(receptDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, receptDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /recepts : get all the recepts.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of recepts in body
     */
    @GetMapping("/recepts")
    @Timed
    public List<ReceptDTO> getAllRecepts(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Recepts");
        return receptService.findAll();
    }

    /**
     * GET  /recepts/:id : get the "id" recept.
     *
     * @param id the id of the receptDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the receptDTO, or with status 404 (Not Found)
     */
    @GetMapping("/recepts/{id}")
    @Timed
    public ResponseEntity<ReceptDTO> getRecept(@PathVariable Long id) {
        log.debug("REST request to get Recept : {}", id);
        Optional<ReceptDTO> receptDTO = receptService.findOne(id);
        return ResponseUtil.wrapOrNotFound(receptDTO);
    }

    /**
     * DELETE  /recepts/:id : delete the "id" recept.
     *
     * @param id the id of the receptDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/recepts/{id}")
    @Timed
    public ResponseEntity<Void> deleteRecept(@PathVariable Long id) {
        log.debug("REST request to delete Recept : {}", id);
        receptService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
