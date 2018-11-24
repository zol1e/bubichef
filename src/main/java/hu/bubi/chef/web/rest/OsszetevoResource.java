package hu.bubi.chef.web.rest;

import com.codahale.metrics.annotation.Timed;
import hu.bubi.chef.service.OsszetevoService;
import hu.bubi.chef.web.rest.errors.BadRequestAlertException;
import hu.bubi.chef.web.rest.util.HeaderUtil;
import hu.bubi.chef.service.dto.OsszetevoDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Osszetevo.
 */
@RestController
@RequestMapping("/api")
public class OsszetevoResource {

    private final Logger log = LoggerFactory.getLogger(OsszetevoResource.class);

    private static final String ENTITY_NAME = "osszetevo";

    private final OsszetevoService osszetevoService;

    public OsszetevoResource(OsszetevoService osszetevoService) {
        this.osszetevoService = osszetevoService;
    }

    /**
     * POST  /osszetevos : Create a new osszetevo.
     *
     * @param osszetevoDTO the osszetevoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new osszetevoDTO, or with status 400 (Bad Request) if the osszetevo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/osszetevos")
    @Timed
    public ResponseEntity<OsszetevoDTO> createOsszetevo(@RequestBody OsszetevoDTO osszetevoDTO) throws URISyntaxException {
        log.debug("REST request to save Osszetevo : {}", osszetevoDTO);
        if (osszetevoDTO.getId() != null) {
            throw new BadRequestAlertException("A new osszetevo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OsszetevoDTO result = osszetevoService.save(osszetevoDTO);
        return ResponseEntity.created(new URI("/api/osszetevos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /osszetevos : Updates an existing osszetevo.
     *
     * @param osszetevoDTO the osszetevoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated osszetevoDTO,
     * or with status 400 (Bad Request) if the osszetevoDTO is not valid,
     * or with status 500 (Internal Server Error) if the osszetevoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/osszetevos")
    @Timed
    public ResponseEntity<OsszetevoDTO> updateOsszetevo(@RequestBody OsszetevoDTO osszetevoDTO) throws URISyntaxException {
        log.debug("REST request to update Osszetevo : {}", osszetevoDTO);
        if (osszetevoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OsszetevoDTO result = osszetevoService.save(osszetevoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, osszetevoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /osszetevos : get all the osszetevos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of osszetevos in body
     */
    @GetMapping("/osszetevos")
    @Timed
    public List<OsszetevoDTO> getAllOsszetevos() {
        log.debug("REST request to get all Osszetevos");
        return osszetevoService.findAll();
    }

    /**
     * GET  /osszetevos/:id : get the "id" osszetevo.
     *
     * @param id the id of the osszetevoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the osszetevoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/osszetevos/{id}")
    @Timed
    public ResponseEntity<OsszetevoDTO> getOsszetevo(@PathVariable Long id) {
        log.debug("REST request to get Osszetevo : {}", id);
        Optional<OsszetevoDTO> osszetevoDTO = osszetevoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(osszetevoDTO);
    }

    /**
     * DELETE  /osszetevos/:id : delete the "id" osszetevo.
     *
     * @param id the id of the osszetevoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/osszetevos/{id}")
    @Timed
    public ResponseEntity<Void> deleteOsszetevo(@PathVariable Long id) {
        log.debug("REST request to delete Osszetevo : {}", id);
        osszetevoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
