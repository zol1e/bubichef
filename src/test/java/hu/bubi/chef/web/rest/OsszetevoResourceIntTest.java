package hu.bubi.chef.web.rest;

import hu.bubi.chef.BubichefApp;

import hu.bubi.chef.domain.Osszetevo;
import hu.bubi.chef.repository.OsszetevoRepository;
import hu.bubi.chef.service.OsszetevoService;
import hu.bubi.chef.service.dto.OsszetevoDTO;
import hu.bubi.chef.service.mapper.OsszetevoMapper;
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
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.util.List;


import static hu.bubi.chef.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the OsszetevoResource REST controller.
 *
 * @see OsszetevoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BubichefApp.class)
public class OsszetevoResourceIntTest {

    private static final String DEFAULT_NEV = "AAAAAAAAAA";
    private static final String UPDATED_NEV = "BBBBBBBBBB";

    private static final String DEFAULT_LEIRAS = "AAAAAAAAAA";
    private static final String UPDATED_LEIRAS = "BBBBBBBBBB";

    private static final byte[] DEFAULT_KEP = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_KEP = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_KEP_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_KEP_CONTENT_TYPE = "image/png";

    @Autowired
    private OsszetevoRepository osszetevoRepository;

    @Autowired
    private OsszetevoMapper osszetevoMapper;

    @Autowired
    private OsszetevoService osszetevoService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restOsszetevoMockMvc;

    private Osszetevo osszetevo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OsszetevoResource osszetevoResource = new OsszetevoResource(osszetevoService);
        this.restOsszetevoMockMvc = MockMvcBuilders.standaloneSetup(osszetevoResource)
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
    public static Osszetevo createEntity(EntityManager em) {
        Osszetevo osszetevo = new Osszetevo()
            .nev(DEFAULT_NEV)
            .leiras(DEFAULT_LEIRAS)
            .kep(DEFAULT_KEP)
            .kepContentType(DEFAULT_KEP_CONTENT_TYPE);
        return osszetevo;
    }

    @Before
    public void initTest() {
        osszetevo = createEntity(em);
    }

    @Test
    @Transactional
    public void createOsszetevo() throws Exception {
        int databaseSizeBeforeCreate = osszetevoRepository.findAll().size();

        // Create the Osszetevo
        OsszetevoDTO osszetevoDTO = osszetevoMapper.toDto(osszetevo);
        restOsszetevoMockMvc.perform(post("/api/osszetevos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(osszetevoDTO)))
            .andExpect(status().isCreated());

        // Validate the Osszetevo in the database
        List<Osszetevo> osszetevoList = osszetevoRepository.findAll();
        assertThat(osszetevoList).hasSize(databaseSizeBeforeCreate + 1);
        Osszetevo testOsszetevo = osszetevoList.get(osszetevoList.size() - 1);
        assertThat(testOsszetevo.getNev()).isEqualTo(DEFAULT_NEV);
        assertThat(testOsszetevo.getLeiras()).isEqualTo(DEFAULT_LEIRAS);
        assertThat(testOsszetevo.getKep()).isEqualTo(DEFAULT_KEP);
        assertThat(testOsszetevo.getKepContentType()).isEqualTo(DEFAULT_KEP_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createOsszetevoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = osszetevoRepository.findAll().size();

        // Create the Osszetevo with an existing ID
        osszetevo.setId(1L);
        OsszetevoDTO osszetevoDTO = osszetevoMapper.toDto(osszetevo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOsszetevoMockMvc.perform(post("/api/osszetevos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(osszetevoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Osszetevo in the database
        List<Osszetevo> osszetevoList = osszetevoRepository.findAll();
        assertThat(osszetevoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllOsszetevos() throws Exception {
        // Initialize the database
        osszetevoRepository.saveAndFlush(osszetevo);

        // Get all the osszetevoList
        restOsszetevoMockMvc.perform(get("/api/osszetevos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(osszetevo.getId().intValue())))
            .andExpect(jsonPath("$.[*].nev").value(hasItem(DEFAULT_NEV.toString())))
            .andExpect(jsonPath("$.[*].leiras").value(hasItem(DEFAULT_LEIRAS.toString())))
            .andExpect(jsonPath("$.[*].kepContentType").value(hasItem(DEFAULT_KEP_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].kep").value(hasItem(Base64Utils.encodeToString(DEFAULT_KEP))));
    }
    
    @Test
    @Transactional
    public void getOsszetevo() throws Exception {
        // Initialize the database
        osszetevoRepository.saveAndFlush(osszetevo);

        // Get the osszetevo
        restOsszetevoMockMvc.perform(get("/api/osszetevos/{id}", osszetevo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(osszetevo.getId().intValue()))
            .andExpect(jsonPath("$.nev").value(DEFAULT_NEV.toString()))
            .andExpect(jsonPath("$.leiras").value(DEFAULT_LEIRAS.toString()))
            .andExpect(jsonPath("$.kepContentType").value(DEFAULT_KEP_CONTENT_TYPE))
            .andExpect(jsonPath("$.kep").value(Base64Utils.encodeToString(DEFAULT_KEP)));
    }

    @Test
    @Transactional
    public void getNonExistingOsszetevo() throws Exception {
        // Get the osszetevo
        restOsszetevoMockMvc.perform(get("/api/osszetevos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOsszetevo() throws Exception {
        // Initialize the database
        osszetevoRepository.saveAndFlush(osszetevo);

        int databaseSizeBeforeUpdate = osszetevoRepository.findAll().size();

        // Update the osszetevo
        Osszetevo updatedOsszetevo = osszetevoRepository.findById(osszetevo.getId()).get();
        // Disconnect from session so that the updates on updatedOsszetevo are not directly saved in db
        em.detach(updatedOsszetevo);
        updatedOsszetevo
            .nev(UPDATED_NEV)
            .leiras(UPDATED_LEIRAS)
            .kep(UPDATED_KEP)
            .kepContentType(UPDATED_KEP_CONTENT_TYPE);
        OsszetevoDTO osszetevoDTO = osszetevoMapper.toDto(updatedOsszetevo);

        restOsszetevoMockMvc.perform(put("/api/osszetevos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(osszetevoDTO)))
            .andExpect(status().isOk());

        // Validate the Osszetevo in the database
        List<Osszetevo> osszetevoList = osszetevoRepository.findAll();
        assertThat(osszetevoList).hasSize(databaseSizeBeforeUpdate);
        Osszetevo testOsszetevo = osszetevoList.get(osszetevoList.size() - 1);
        assertThat(testOsszetevo.getNev()).isEqualTo(UPDATED_NEV);
        assertThat(testOsszetevo.getLeiras()).isEqualTo(UPDATED_LEIRAS);
        assertThat(testOsszetevo.getKep()).isEqualTo(UPDATED_KEP);
        assertThat(testOsszetevo.getKepContentType()).isEqualTo(UPDATED_KEP_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingOsszetevo() throws Exception {
        int databaseSizeBeforeUpdate = osszetevoRepository.findAll().size();

        // Create the Osszetevo
        OsszetevoDTO osszetevoDTO = osszetevoMapper.toDto(osszetevo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOsszetevoMockMvc.perform(put("/api/osszetevos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(osszetevoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Osszetevo in the database
        List<Osszetevo> osszetevoList = osszetevoRepository.findAll();
        assertThat(osszetevoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOsszetevo() throws Exception {
        // Initialize the database
        osszetevoRepository.saveAndFlush(osszetevo);

        int databaseSizeBeforeDelete = osszetevoRepository.findAll().size();

        // Get the osszetevo
        restOsszetevoMockMvc.perform(delete("/api/osszetevos/{id}", osszetevo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Osszetevo> osszetevoList = osszetevoRepository.findAll();
        assertThat(osszetevoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Osszetevo.class);
        Osszetevo osszetevo1 = new Osszetevo();
        osszetevo1.setId(1L);
        Osszetevo osszetevo2 = new Osszetevo();
        osszetevo2.setId(osszetevo1.getId());
        assertThat(osszetevo1).isEqualTo(osszetevo2);
        osszetevo2.setId(2L);
        assertThat(osszetevo1).isNotEqualTo(osszetevo2);
        osszetevo1.setId(null);
        assertThat(osszetevo1).isNotEqualTo(osszetevo2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OsszetevoDTO.class);
        OsszetevoDTO osszetevoDTO1 = new OsszetevoDTO();
        osszetevoDTO1.setId(1L);
        OsszetevoDTO osszetevoDTO2 = new OsszetevoDTO();
        assertThat(osszetevoDTO1).isNotEqualTo(osszetevoDTO2);
        osszetevoDTO2.setId(osszetevoDTO1.getId());
        assertThat(osszetevoDTO1).isEqualTo(osszetevoDTO2);
        osszetevoDTO2.setId(2L);
        assertThat(osszetevoDTO1).isNotEqualTo(osszetevoDTO2);
        osszetevoDTO1.setId(null);
        assertThat(osszetevoDTO1).isNotEqualTo(osszetevoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(osszetevoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(osszetevoMapper.fromId(null)).isNull();
    }
}
