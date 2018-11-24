package hu.bubi.chef.web.rest;

import hu.bubi.chef.BubichefApp;

import hu.bubi.chef.domain.Kategoria;
import hu.bubi.chef.repository.KategoriaRepository;
import hu.bubi.chef.service.KategoriaService;
import hu.bubi.chef.service.dto.KategoriaDTO;
import hu.bubi.chef.service.mapper.KategoriaMapper;
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
 * Test class for the KategoriaResource REST controller.
 *
 * @see KategoriaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BubichefApp.class)
public class KategoriaResourceIntTest {

    private static final String DEFAULT_NEV = "AAAAAAAAAA";
    private static final String UPDATED_NEV = "BBBBBBBBBB";

    @Autowired
    private KategoriaRepository kategoriaRepository;

    @Autowired
    private KategoriaMapper kategoriaMapper;

    @Autowired
    private KategoriaService kategoriaService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restKategoriaMockMvc;

    private Kategoria kategoria;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final KategoriaResource kategoriaResource = new KategoriaResource(kategoriaService);
        this.restKategoriaMockMvc = MockMvcBuilders.standaloneSetup(kategoriaResource)
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
    public static Kategoria createEntity(EntityManager em) {
        Kategoria kategoria = new Kategoria()
            .nev(DEFAULT_NEV);
        return kategoria;
    }

    @Before
    public void initTest() {
        kategoria = createEntity(em);
    }

    @Test
    @Transactional
    public void createKategoria() throws Exception {
        int databaseSizeBeforeCreate = kategoriaRepository.findAll().size();

        // Create the Kategoria
        KategoriaDTO kategoriaDTO = kategoriaMapper.toDto(kategoria);
        restKategoriaMockMvc.perform(post("/api/kategorias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kategoriaDTO)))
            .andExpect(status().isCreated());

        // Validate the Kategoria in the database
        List<Kategoria> kategoriaList = kategoriaRepository.findAll();
        assertThat(kategoriaList).hasSize(databaseSizeBeforeCreate + 1);
        Kategoria testKategoria = kategoriaList.get(kategoriaList.size() - 1);
        assertThat(testKategoria.getNev()).isEqualTo(DEFAULT_NEV);
    }

    @Test
    @Transactional
    public void createKategoriaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = kategoriaRepository.findAll().size();

        // Create the Kategoria with an existing ID
        kategoria.setId(1L);
        KategoriaDTO kategoriaDTO = kategoriaMapper.toDto(kategoria);

        // An entity with an existing ID cannot be created, so this API call must fail
        restKategoriaMockMvc.perform(post("/api/kategorias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kategoriaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Kategoria in the database
        List<Kategoria> kategoriaList = kategoriaRepository.findAll();
        assertThat(kategoriaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllKategorias() throws Exception {
        // Initialize the database
        kategoriaRepository.saveAndFlush(kategoria);

        // Get all the kategoriaList
        restKategoriaMockMvc.perform(get("/api/kategorias?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(kategoria.getId().intValue())))
            .andExpect(jsonPath("$.[*].nev").value(hasItem(DEFAULT_NEV.toString())));
    }
    
    @Test
    @Transactional
    public void getKategoria() throws Exception {
        // Initialize the database
        kategoriaRepository.saveAndFlush(kategoria);

        // Get the kategoria
        restKategoriaMockMvc.perform(get("/api/kategorias/{id}", kategoria.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(kategoria.getId().intValue()))
            .andExpect(jsonPath("$.nev").value(DEFAULT_NEV.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingKategoria() throws Exception {
        // Get the kategoria
        restKategoriaMockMvc.perform(get("/api/kategorias/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateKategoria() throws Exception {
        // Initialize the database
        kategoriaRepository.saveAndFlush(kategoria);

        int databaseSizeBeforeUpdate = kategoriaRepository.findAll().size();

        // Update the kategoria
        Kategoria updatedKategoria = kategoriaRepository.findById(kategoria.getId()).get();
        // Disconnect from session so that the updates on updatedKategoria are not directly saved in db
        em.detach(updatedKategoria);
        updatedKategoria
            .nev(UPDATED_NEV);
        KategoriaDTO kategoriaDTO = kategoriaMapper.toDto(updatedKategoria);

        restKategoriaMockMvc.perform(put("/api/kategorias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kategoriaDTO)))
            .andExpect(status().isOk());

        // Validate the Kategoria in the database
        List<Kategoria> kategoriaList = kategoriaRepository.findAll();
        assertThat(kategoriaList).hasSize(databaseSizeBeforeUpdate);
        Kategoria testKategoria = kategoriaList.get(kategoriaList.size() - 1);
        assertThat(testKategoria.getNev()).isEqualTo(UPDATED_NEV);
    }

    @Test
    @Transactional
    public void updateNonExistingKategoria() throws Exception {
        int databaseSizeBeforeUpdate = kategoriaRepository.findAll().size();

        // Create the Kategoria
        KategoriaDTO kategoriaDTO = kategoriaMapper.toDto(kategoria);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKategoriaMockMvc.perform(put("/api/kategorias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kategoriaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Kategoria in the database
        List<Kategoria> kategoriaList = kategoriaRepository.findAll();
        assertThat(kategoriaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteKategoria() throws Exception {
        // Initialize the database
        kategoriaRepository.saveAndFlush(kategoria);

        int databaseSizeBeforeDelete = kategoriaRepository.findAll().size();

        // Get the kategoria
        restKategoriaMockMvc.perform(delete("/api/kategorias/{id}", kategoria.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Kategoria> kategoriaList = kategoriaRepository.findAll();
        assertThat(kategoriaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Kategoria.class);
        Kategoria kategoria1 = new Kategoria();
        kategoria1.setId(1L);
        Kategoria kategoria2 = new Kategoria();
        kategoria2.setId(kategoria1.getId());
        assertThat(kategoria1).isEqualTo(kategoria2);
        kategoria2.setId(2L);
        assertThat(kategoria1).isNotEqualTo(kategoria2);
        kategoria1.setId(null);
        assertThat(kategoria1).isNotEqualTo(kategoria2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(KategoriaDTO.class);
        KategoriaDTO kategoriaDTO1 = new KategoriaDTO();
        kategoriaDTO1.setId(1L);
        KategoriaDTO kategoriaDTO2 = new KategoriaDTO();
        assertThat(kategoriaDTO1).isNotEqualTo(kategoriaDTO2);
        kategoriaDTO2.setId(kategoriaDTO1.getId());
        assertThat(kategoriaDTO1).isEqualTo(kategoriaDTO2);
        kategoriaDTO2.setId(2L);
        assertThat(kategoriaDTO1).isNotEqualTo(kategoriaDTO2);
        kategoriaDTO1.setId(null);
        assertThat(kategoriaDTO1).isNotEqualTo(kategoriaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(kategoriaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(kategoriaMapper.fromId(null)).isNull();
    }
}
