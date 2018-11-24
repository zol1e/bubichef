package hu.bubi.chef.web.rest;

import hu.bubi.chef.BubichefApp;

import hu.bubi.chef.domain.Recept;
import hu.bubi.chef.repository.ReceptRepository;
import hu.bubi.chef.service.ReceptService;
import hu.bubi.chef.service.dto.ReceptDTO;
import hu.bubi.chef.service.mapper.ReceptMapper;
import hu.bubi.chef.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;


import static hu.bubi.chef.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ReceptResource REST controller.
 *
 * @see ReceptResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BubichefApp.class)
public class ReceptResourceIntTest {

    private static final String DEFAULT_NEV = "AAAAAAAAAA";
    private static final String UPDATED_NEV = "BBBBBBBBBB";

    private static final String DEFAULT_LEIRAS = "AAAAAAAAAA";
    private static final String UPDATED_LEIRAS = "BBBBBBBBBB";

    private static final byte[] DEFAULT_KEP = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_KEP = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_KEP_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_KEP_CONTENT_TYPE = "image/png";

    private static final LocalDate DEFAULT_FELTOLTVE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FELTOLTVE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private ReceptRepository receptRepository;

    @Mock
    private ReceptRepository receptRepositoryMock;

    @Autowired
    private ReceptMapper receptMapper;

    @Mock
    private ReceptService receptServiceMock;

    @Autowired
    private ReceptService receptService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restReceptMockMvc;

    private Recept recept;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ReceptResource receptResource = new ReceptResource(receptService);
        this.restReceptMockMvc = MockMvcBuilders.standaloneSetup(receptResource)
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
    public static Recept createEntity(EntityManager em) {
        Recept recept = new Recept()
            .nev(DEFAULT_NEV)
            .leiras(DEFAULT_LEIRAS)
            .kep(DEFAULT_KEP)
            .kepContentType(DEFAULT_KEP_CONTENT_TYPE)
            .feltoltve(DEFAULT_FELTOLTVE);
        return recept;
    }

    @Before
    public void initTest() {
        recept = createEntity(em);
    }

    @Test
    @Transactional
    public void createRecept() throws Exception {
        int databaseSizeBeforeCreate = receptRepository.findAll().size();

        // Create the Recept
        ReceptDTO receptDTO = receptMapper.toDto(recept);
        restReceptMockMvc.perform(post("/api/recepts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(receptDTO)))
            .andExpect(status().isCreated());

        // Validate the Recept in the database
        List<Recept> receptList = receptRepository.findAll();
        assertThat(receptList).hasSize(databaseSizeBeforeCreate + 1);
        Recept testRecept = receptList.get(receptList.size() - 1);
        assertThat(testRecept.getNev()).isEqualTo(DEFAULT_NEV);
        assertThat(testRecept.getLeiras()).isEqualTo(DEFAULT_LEIRAS);
        assertThat(testRecept.getKep()).isEqualTo(DEFAULT_KEP);
        assertThat(testRecept.getKepContentType()).isEqualTo(DEFAULT_KEP_CONTENT_TYPE);
        assertThat(testRecept.getFeltoltve()).isEqualTo(DEFAULT_FELTOLTVE);
    }

    @Test
    @Transactional
    public void createReceptWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = receptRepository.findAll().size();

        // Create the Recept with an existing ID
        recept.setId(1L);
        ReceptDTO receptDTO = receptMapper.toDto(recept);

        // An entity with an existing ID cannot be created, so this API call must fail
        restReceptMockMvc.perform(post("/api/recepts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(receptDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Recept in the database
        List<Recept> receptList = receptRepository.findAll();
        assertThat(receptList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllRecepts() throws Exception {
        // Initialize the database
        receptRepository.saveAndFlush(recept);

        // Get all the receptList
        restReceptMockMvc.perform(get("/api/recepts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(recept.getId().intValue())))
            .andExpect(jsonPath("$.[*].nev").value(hasItem(DEFAULT_NEV.toString())))
            .andExpect(jsonPath("$.[*].leiras").value(hasItem(DEFAULT_LEIRAS.toString())))
            .andExpect(jsonPath("$.[*].kepContentType").value(hasItem(DEFAULT_KEP_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].kep").value(hasItem(Base64Utils.encodeToString(DEFAULT_KEP))))
            .andExpect(jsonPath("$.[*].feltoltve").value(hasItem(DEFAULT_FELTOLTVE.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllReceptsWithEagerRelationshipsIsEnabled() throws Exception {
        ReceptResource receptResource = new ReceptResource(receptServiceMock);
        when(receptServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restReceptMockMvc = MockMvcBuilders.standaloneSetup(receptResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restReceptMockMvc.perform(get("/api/recepts?eagerload=true"))
        .andExpect(status().isOk());

        verify(receptServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllReceptsWithEagerRelationshipsIsNotEnabled() throws Exception {
        ReceptResource receptResource = new ReceptResource(receptServiceMock);
            when(receptServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restReceptMockMvc = MockMvcBuilders.standaloneSetup(receptResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restReceptMockMvc.perform(get("/api/recepts?eagerload=true"))
        .andExpect(status().isOk());

            verify(receptServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getRecept() throws Exception {
        // Initialize the database
        receptRepository.saveAndFlush(recept);

        // Get the recept
        restReceptMockMvc.perform(get("/api/recepts/{id}", recept.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(recept.getId().intValue()))
            .andExpect(jsonPath("$.nev").value(DEFAULT_NEV.toString()))
            .andExpect(jsonPath("$.leiras").value(DEFAULT_LEIRAS.toString()))
            .andExpect(jsonPath("$.kepContentType").value(DEFAULT_KEP_CONTENT_TYPE))
            .andExpect(jsonPath("$.kep").value(Base64Utils.encodeToString(DEFAULT_KEP)))
            .andExpect(jsonPath("$.feltoltve").value(DEFAULT_FELTOLTVE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRecept() throws Exception {
        // Get the recept
        restReceptMockMvc.perform(get("/api/recepts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRecept() throws Exception {
        // Initialize the database
        receptRepository.saveAndFlush(recept);

        int databaseSizeBeforeUpdate = receptRepository.findAll().size();

        // Update the recept
        Recept updatedRecept = receptRepository.findById(recept.getId()).get();
        // Disconnect from session so that the updates on updatedRecept are not directly saved in db
        em.detach(updatedRecept);
        updatedRecept
            .nev(UPDATED_NEV)
            .leiras(UPDATED_LEIRAS)
            .kep(UPDATED_KEP)
            .kepContentType(UPDATED_KEP_CONTENT_TYPE)
            .feltoltve(UPDATED_FELTOLTVE);
        ReceptDTO receptDTO = receptMapper.toDto(updatedRecept);

        restReceptMockMvc.perform(put("/api/recepts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(receptDTO)))
            .andExpect(status().isOk());

        // Validate the Recept in the database
        List<Recept> receptList = receptRepository.findAll();
        assertThat(receptList).hasSize(databaseSizeBeforeUpdate);
        Recept testRecept = receptList.get(receptList.size() - 1);
        assertThat(testRecept.getNev()).isEqualTo(UPDATED_NEV);
        assertThat(testRecept.getLeiras()).isEqualTo(UPDATED_LEIRAS);
        assertThat(testRecept.getKep()).isEqualTo(UPDATED_KEP);
        assertThat(testRecept.getKepContentType()).isEqualTo(UPDATED_KEP_CONTENT_TYPE);
        assertThat(testRecept.getFeltoltve()).isEqualTo(UPDATED_FELTOLTVE);
    }

    @Test
    @Transactional
    public void updateNonExistingRecept() throws Exception {
        int databaseSizeBeforeUpdate = receptRepository.findAll().size();

        // Create the Recept
        ReceptDTO receptDTO = receptMapper.toDto(recept);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReceptMockMvc.perform(put("/api/recepts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(receptDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Recept in the database
        List<Recept> receptList = receptRepository.findAll();
        assertThat(receptList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRecept() throws Exception {
        // Initialize the database
        receptRepository.saveAndFlush(recept);

        int databaseSizeBeforeDelete = receptRepository.findAll().size();

        // Get the recept
        restReceptMockMvc.perform(delete("/api/recepts/{id}", recept.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Recept> receptList = receptRepository.findAll();
        assertThat(receptList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Recept.class);
        Recept recept1 = new Recept();
        recept1.setId(1L);
        Recept recept2 = new Recept();
        recept2.setId(recept1.getId());
        assertThat(recept1).isEqualTo(recept2);
        recept2.setId(2L);
        assertThat(recept1).isNotEqualTo(recept2);
        recept1.setId(null);
        assertThat(recept1).isNotEqualTo(recept2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReceptDTO.class);
        ReceptDTO receptDTO1 = new ReceptDTO();
        receptDTO1.setId(1L);
        ReceptDTO receptDTO2 = new ReceptDTO();
        assertThat(receptDTO1).isNotEqualTo(receptDTO2);
        receptDTO2.setId(receptDTO1.getId());
        assertThat(receptDTO1).isEqualTo(receptDTO2);
        receptDTO2.setId(2L);
        assertThat(receptDTO1).isNotEqualTo(receptDTO2);
        receptDTO1.setId(null);
        assertThat(receptDTO1).isNotEqualTo(receptDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(receptMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(receptMapper.fromId(null)).isNull();
    }
}
