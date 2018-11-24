package hu.bubi.chef.web.rest;

import com.codahale.metrics.annotation.Timed;
import hu.bubi.chef.service.KategoriaService;
import hu.bubi.chef.web.rest.errors.BadRequestAlertException;
import hu.bubi.chef.web.rest.util.HeaderUtil;
import hu.bubi.chef.service.dto.KategoriaDTO;
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
 * REST controller for managing Kategoria.
 */
@RestController
@RequestMapping("/api")
public class KategoriaResource {

    private final Logger log = LoggerFactory.getLogger(KategoriaResource.class);

    private static final String ENTITY_NAME = "kategoria";

    private final KategoriaService kategoriaService;

    public KategoriaResource(KategoriaService kategoriaService) {
        this.kategoriaService = kategoriaService;
    }

    /**
     * POST  /kategorias : Create a new kategoria.
     *
     * @param kategoriaDTO the kategoriaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new kategoriaDTO, or with status 400 (Bad Request) if the kategoria has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/kategorias")
    @Timed
    public ResponseEntity<KategoriaDTO> createKategoria(@RequestBody KategoriaDTO kategoriaDTO) throws URISyntaxException {
        log.debug("REST request to save Kategoria : {}", kategoriaDTO);
        if (kategoriaDTO.getId() != null) {
            throw new BadRequestAlertException("A new kategoria cannot already have an ID", ENTITY_NAME, "idexists");
        }
        KategoriaDTO result = kategoriaService.save(kategoriaDTO);
        return ResponseEntity.created(new URI("/api/kategorias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /kategorias : Updates an existing kategoria.
     *
     * @param kategoriaDTO the kategoriaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated kategoriaDTO,
     * or with status 400 (Bad Request) if the kategoriaDTO is not valid,
     * or with status 500 (Internal Server Error) if the kategoriaDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/kategorias")
    @Timed
    public ResponseEntity<KategoriaDTO> updateKategoria(@RequestBody KategoriaDTO kategoriaDTO) throws URISyntaxException {
        log.debug("REST request to update Kategoria : {}", kategoriaDTO);
        if (kategoriaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        KategoriaDTO result = kategoriaService.save(kategoriaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, kategoriaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /kategorias : get all the kategorias.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of kategorias in body
     */
    @GetMapping("/kategorias")
    @Timed
    public List<KategoriaDTO> getAllKategorias() {
        log.debug("REST request to get all Kategorias");
        return kategoriaService.findAll();
    }

    /**
     * GET  /kategorias/:id : get the "id" kategoria.
     *
     * @param id the id of the kategoriaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the kategoriaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/kategorias/{id}")
    @Timed
    public ResponseEntity<KategoriaDTO> getKategoria(@PathVariable Long id) {
        log.debug("REST request to get Kategoria : {}", id);
        Optional<KategoriaDTO> kategoriaDTO = kategoriaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(kategoriaDTO);
    }

    /**
     * DELETE  /kategorias/:id : delete the "id" kategoria.
     *
     * @param id the id of the kategoriaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/kategorias/{id}")
    @Timed
    public ResponseEntity<Void> deleteKategoria(@PathVariable Long id) {
        log.debug("REST request to delete Kategoria : {}", id);
        kategoriaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
