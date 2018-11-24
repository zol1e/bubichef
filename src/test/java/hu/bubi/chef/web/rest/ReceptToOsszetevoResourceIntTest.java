package hu.bubi.chef.web.rest;

import hu.bubi.chef.BubichefApp;

import hu.bubi.chef.domain.ReceptToOsszetevo;
import hu.bubi.chef.repository.ReceptToOsszetevoRepository;
import hu.bubi.chef.service.ReceptToOsszetevoService;
import hu.bubi.chef.service.dto.ReceptToOsszetevoDTO;
import hu.bubi.chef.service.mapper.ReceptToOsszetevoMapper;
import hu.bubi.chef.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;


import static hu.bubi.chef.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ReceptToOsszetevoResource REST controller.
 *
 * @see ReceptToOsszetevoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BubichefApp.class)
public class ReceptToOsszetevoResourceIntTest {

    private static final String DEFAULT_MENNYISEG = "AAAAAAAAAA";
    private static final String UPDATED_MENNYISEG = "BBBBBBBBBB";

    private static final String DEFAULT_MEGJEGYZES = "AAAAAAAAAA";
    private static final String UPDATED_MEGJEGYZES = "BBBBBBBBBB";

    @Autowired
    private ReceptToOsszetevoRepository receptToOsszetevoRepository;

    @Autowired
    private ReceptToOsszetevoMapper receptToOsszetevoMapper;

    @Autowired
    private ReceptToOsszetevoService receptToOsszetevoService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restReceptToOsszetevoMockMvc;

    private ReceptToOsszetevo receptToOsszetevo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ReceptToOsszetevoResource receptToOsszetevoResource = new ReceptToOsszetevoResource(receptToOsszetevoService);
        this.restReceptToOsszetevoMockMvc = MockMvcBuilders.standaloneSetup(receptToOsszetevoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ReceptToOsszetevo createEntity(EntityManager em) {
        ReceptToOsszetevo receptToOsszetevo = new ReceptToOsszetevo()
            .mennyiseg(DEFAULT_MENNYISEG)
            .megjegyzes(DEFAULT_MEGJEGYZES);
        return receptToOsszetevo;
    }

    @Before
    public void initTest() {
        receptToOsszetevo = createEntity(em);
    }

    @Test
    @Transactional
    public void createReceptToOsszetevo() throws Exception {
        int databaseSizeBeforeCreate = receptToOsszetevoRepository.findAll().size();

        // Create the ReceptToOsszetevo
        ReceptToOsszetevoDTO receptToOsszetevoDTO = receptToOsszetevoMapper.toDto(receptToOsszetevo);
        restReceptToOsszetevoMockMvc.perform(post("/api/recept-to-osszetevos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(receptToOsszetevoDTO)))
            .andExpect(status().isCreated());

        // Validate the ReceptToOsszetevo in the database
        List<ReceptToOsszetevo> receptToOsszetevoList = receptToOsszetevoRepository.findAll();
        assertThat(receptToOsszetevoList).hasSize(databaseSizeBeforeCreate + 1);
        ReceptToOsszetevo testReceptToOsszetevo = receptToOsszetevoList.get(receptToOsszetevoList.size() - 1);
        assertThat(testReceptToOsszetevo.getMennyiseg()).isEqualTo(DEFAULT_MENNYISEG);
        assertThat(testReceptToOsszetevo.getMegjegyzes()).isEqualTo(DEFAULT_MEGJEGYZES);
    }

    @Test
    @Transactional
    public void createReceptToOsszetevoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = receptToOsszetevoRepository.findAll().size();

        // Create the ReceptToOsszetevo with an existing ID
        receptToOsszetevo.setId(1L);
        ReceptToOsszetevoDTO receptToOsszetevoDTO = receptToOsszetevoMapper.toDto(receptToOsszetevo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restReceptToOsszetevoMockMvc.perform(post("/api/recept-to-osszetevos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(receptToOsszetevoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ReceptToOsszetevo in the database
        List<ReceptToOsszetevo> receptToOsszetevoList = receptToOsszetevoRepository.findAll();
        assertThat(receptToOsszetevoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllReceptToOsszetevos() throws Exception {
        // Initialize the database
        receptToOsszetevoRepository.saveAndFlush(receptToOsszetevo);

        // Get all the receptToOsszetevoList
        restReceptToOsszetevoMockMvc.perform(get("/api/recept-to-osszetevos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(receptToOsszetevo.getId().intValue())))
            .andExpect(jsonPath("$.[*].mennyiseg").value(hasItem(DEFAULT_MENNYISEG.toString())))
            .andExpect(jsonPath("$.[*].megjegyzes").value(hasItem(DEFAULT_MEGJEGYZES.toString())));
    }
    
    @Test
    @Transactional
    public void getReceptToOsszetevo() throws Exception {
        // Initialize the database
        receptToOsszetevoRepository.saveAndFlush(receptToOsszetevo);

        // Get the receptToOsszetevo
        restReceptToOsszetevoMockMvc.perform(get("/api/recept-to-osszetevos/{id}", receptToOsszetevo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(receptToOsszetevo.getId().intValue()))
            .andExpect(jsonPath("$.mennyiseg").value(DEFAULT_MENNYISEG.toString()))
            .andExpect(jsonPath("$.megjegyzes").value(DEFAULT_MEGJEGYZES.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingReceptToOsszetevo() throws Exception {
        // Get the receptToOsszetevo
        restReceptToOsszetevoMockMvc.perform(get("/api/recept-to-osszetevos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReceptToOsszetevo() throws Exception {
        // Initialize the database
        receptToOsszetevoRepository.saveAndFlush(receptToOsszetevo);

        int databaseSizeBeforeUpdate = receptToOsszetevoRepository.findAll().size();

        // Update the receptToOsszetevo
        ReceptToOsszetevo updatedReceptToOsszetevo = receptToOsszetevoRepository.findById(receptToOsszetevo.getId()).get();
        // Disconnect from session so that the updates on updatedReceptToOsszetevo are not directly saved in db
        em.detach(updatedReceptToOsszetevo);
        updatedReceptToOsszetevo
            .mennyiseg(UPDATED_MENNYISEG)
            .megjegyzes(UPDATED_MEGJEGYZES);
        ReceptToOsszetevoDTO receptToOsszetevoDTO = receptToOsszetevoMapper.toDto(updatedReceptToOsszetevo);

        restReceptToOsszetevoMockMvc.perform(put("/api/recept-to-osszetevos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(receptToOsszetevoDTO)))
            .andExpect(status().isOk());

        // Validate the ReceptToOsszetevo in the database
        List<ReceptToOsszetevo> receptToOsszetevoList = receptToOsszetevoRepository.findAll();
        assertThat(receptToOsszetevoList).hasSize(databaseSizeBeforeUpdate);
        ReceptToOsszetevo testReceptToOsszetevo = receptToOsszetevoList.get(receptToOsszetevoList.size() - 1);
        assertThat(testReceptToOsszetevo.getMennyiseg()).isEqualTo(UPDATED_MENNYISEG);
        assertThat(testReceptToOsszetevo.getMegjegyzes()).isEqualTo(UPDATED_MEGJEGYZES);
    }

    @Test
    @Transactional
    public void updateNonExistingReceptToOsszetevo() throws Exception {
        int databaseSizeBeforeUpdate = receptToOsszetevoRepository.findAll().size();

        // Create the ReceptToOsszetevo
        ReceptToOsszetevoDTO receptToOsszetevoDTO = receptToOsszetevoMapper.toDto(receptToOsszetevo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReceptToOsszetevoMockMvc.perform(put("/api/recept-to-osszetevos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(receptToOsszetevoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ReceptToOsszetevo in the database
        List<ReceptToOsszetevo> receptToOsszetevoList = receptToOsszetevoRepository.findAll();
        assertThat(receptToOsszetevoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteReceptToOsszetevo() throws Exception {
        // Initialize the database
        receptToOsszetevoRepository.saveAndFlush(receptToOsszetevo);

        int databaseSizeBeforeDelete = receptToOsszetevoRepository.findAll().size();

        // Get the receptToOsszetevo
        restReceptToOsszetevoMockMvc.perform(delete("/api/recept-to-osszetevos/{id}", receptToOsszetevo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ReceptToOsszetevo> receptToOsszetevoList = receptToOsszetevoRepository.findAll();
        assertThat(receptToOsszetevoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReceptToOsszetevo.class);
        ReceptToOsszetevo receptToOsszetevo1 = new ReceptToOsszetevo();
        receptToOsszetevo1.setId(1L);
        ReceptToOsszetevo receptToOsszetevo2 = new ReceptToOsszetevo();
        receptToOsszetevo2.setId(receptToOsszetevo1.getId());
        assertThat(receptToOsszetevo1).isEqualTo(receptToOsszetevo2);
        receptToOsszetevo2.setId(2L);
        assertThat(receptToOsszetevo1).isNotEqualTo(receptToOsszetevo2);
        receptToOsszetevo1.setId(null);
        assertThat(receptToOsszetevo1).isNotEqualTo(receptToOsszetevo2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReceptToOsszetevoDTO.class);
        ReceptToOsszetevoDTO receptToOsszetevoDTO1 = new ReceptToOsszetevoDTO();
        receptToOsszetevoDTO1.setId(1L);
        ReceptToOsszetevoDTO receptToOsszetevoDTO2 = new ReceptToOsszetevoDTO();
        assertThat(receptToOsszetevoDTO1).isNotEqualTo(receptToOsszetevoDTO2);
        receptToOsszetevoDTO2.setId(receptToOsszetevoDTO1.getId());
        assertThat(receptToOsszetevoDTO1).isEqualTo(receptToOsszetevoDTO2);
        receptToOsszetevoDTO2.setId(2L);
        assertThat(receptToOsszetevoDTO1).isNotEqualTo(receptToOsszetevoDTO2);
        receptToOsszetevoDTO1.setId(null);
        assertThat(receptToOsszetevoDTO1).isNotEqualTo(receptToOsszetevoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(receptToOsszetevoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(receptToOsszetevoMapper.fromId(null)).isNull();
    }
}
