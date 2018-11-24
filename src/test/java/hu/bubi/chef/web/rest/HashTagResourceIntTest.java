package hu.bubi.chef.web.rest;

import hu.bubi.chef.BubichefApp;

import hu.bubi.chef.domain.HashTag;
import hu.bubi.chef.repository.HashTagRepository;
import hu.bubi.chef.service.HashTagService;
import hu.bubi.chef.service.dto.HashTagDTO;
import hu.bubi.chef.service.mapper.HashTagMapper;
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
 * Test class for the HashTagResource REST controller.
 *
 * @see HashTagResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BubichefApp.class)
public class HashTagResourceIntTest {

    private static final String DEFAULT_NEV = "AAAAAAAAAA";
    private static final String UPDATED_NEV = "BBBBBBBBBB";

    @Autowired
    private HashTagRepository hashTagRepository;

    @Autowired
    private HashTagMapper hashTagMapper;

    @Autowired
    private HashTagService hashTagService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restHashTagMockMvc;

    private HashTag hashTag;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final HashTagResource hashTagResource = new HashTagResource(hashTagService);
        this.restHashTagMockMvc = MockMvcBuilders.standaloneSetup(hashTagResource)
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
    public static HashTag createEntity(EntityManager em) {
        HashTag hashTag = new HashTag()
            .nev(DEFAULT_NEV);
        return hashTag;
    }

    @Before
    public void initTest() {
        hashTag = createEntity(em);
    }

    @Test
    @Transactional
    public void createHashTag() throws Exception {
        int databaseSizeBeforeCreate = hashTagRepository.findAll().size();

        // Create the HashTag
        HashTagDTO hashTagDTO = hashTagMapper.toDto(hashTag);
        restHashTagMockMvc.perform(post("/api/hash-tags")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hashTagDTO)))
            .andExpect(status().isCreated());

        // Validate the HashTag in the database
        List<HashTag> hashTagList = hashTagRepository.findAll();
        assertThat(hashTagList).hasSize(databaseSizeBeforeCreate + 1);
        HashTag testHashTag = hashTagList.get(hashTagList.size() - 1);
        assertThat(testHashTag.getNev()).isEqualTo(DEFAULT_NEV);
    }

    @Test
    @Transactional
    public void createHashTagWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = hashTagRepository.findAll().size();

        // Create the HashTag with an existing ID
        hashTag.setId(1L);
        HashTagDTO hashTagDTO = hashTagMapper.toDto(hashTag);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHashTagMockMvc.perform(post("/api/hash-tags")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hashTagDTO)))
            .andExpect(status().isBadRequest());

        // Validate the HashTag in the database
        List<HashTag> hashTagList = hashTagRepository.findAll();
        assertThat(hashTagList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllHashTags() throws Exception {
        // Initialize the database
        hashTagRepository.saveAndFlush(hashTag);

        // Get all the hashTagList
        restHashTagMockMvc.perform(get("/api/hash-tags?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hashTag.getId().intValue())))
            .andExpect(jsonPath("$.[*].nev").value(hasItem(DEFAULT_NEV.toString())));
    }
    
    @Test
    @Transactional
    public void getHashTag() throws Exception {
        // Initialize the database
        hashTagRepository.saveAndFlush(hashTag);

        // Get the hashTag
        restHashTagMockMvc.perform(get("/api/hash-tags/{id}", hashTag.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(hashTag.getId().intValue()))
            .andExpect(jsonPath("$.nev").value(DEFAULT_NEV.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingHashTag() throws Exception {
        // Get the hashTag
        restHashTagMockMvc.perform(get("/api/hash-tags/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHashTag() throws Exception {
        // Initialize the database
        hashTagRepository.saveAndFlush(hashTag);

        int databaseSizeBeforeUpdate = hashTagRepository.findAll().size();

        // Update the hashTag
        HashTag updatedHashTag = hashTagRepository.findById(hashTag.getId()).get();
        // Disconnect from session so that the updates on updatedHashTag are not directly saved in db
        em.detach(updatedHashTag);
        updatedHashTag
            .nev(UPDATED_NEV);
        HashTagDTO hashTagDTO = hashTagMapper.toDto(updatedHashTag);

        restHashTagMockMvc.perform(put("/api/hash-tags")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hashTagDTO)))
            .andExpect(status().isOk());

        // Validate the HashTag in the database
        List<HashTag> hashTagList = hashTagRepository.findAll();
        assertThat(hashTagList).hasSize(databaseSizeBeforeUpdate);
        HashTag testHashTag = hashTagList.get(hashTagList.size() - 1);
        assertThat(testHashTag.getNev()).isEqualTo(UPDATED_NEV);
    }

    @Test
    @Transactional
    public void updateNonExistingHashTag() throws Exception {
        int databaseSizeBeforeUpdate = hashTagRepository.findAll().size();

        // Create the HashTag
        HashTagDTO hashTagDTO = hashTagMapper.toDto(hashTag);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHashTagMockMvc.perform(put("/api/hash-tags")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hashTagDTO)))
            .andExpect(status().isBadRequest());

        // Validate the HashTag in the database
        List<HashTag> hashTagList = hashTagRepository.findAll();
        assertThat(hashTagList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteHashTag() throws Exception {
        // Initialize the database
        hashTagRepository.saveAndFlush(hashTag);

        int databaseSizeBeforeDelete = hashTagRepository.findAll().size();

        // Get the hashTag
        restHashTagMockMvc.perform(delete("/api/hash-tags/{id}", hashTag.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<HashTag> hashTagList = hashTagRepository.findAll();
        assertThat(hashTagList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HashTag.class);
        HashTag hashTag1 = new HashTag();
        hashTag1.setId(1L);
        HashTag hashTag2 = new HashTag();
        hashTag2.setId(hashTag1.getId());
        assertThat(hashTag1).isEqualTo(hashTag2);
        hashTag2.setId(2L);
        assertThat(hashTag1).isNotEqualTo(hashTag2);
        hashTag1.setId(null);
        assertThat(hashTag1).isNotEqualTo(hashTag2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(HashTagDTO.class);
        HashTagDTO hashTagDTO1 = new HashTagDTO();
        hashTagDTO1.setId(1L);
        HashTagDTO hashTagDTO2 = new HashTagDTO();
        assertThat(hashTagDTO1).isNotEqualTo(hashTagDTO2);
        hashTagDTO2.setId(hashTagDTO1.getId());
        assertThat(hashTagDTO1).isEqualTo(hashTagDTO2);
        hashTagDTO2.setId(2L);
        assertThat(hashTagDTO1).isNotEqualTo(hashTagDTO2);
        hashTagDTO1.setId(null);
        assertThat(hashTagDTO1).isNotEqualTo(hashTagDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(hashTagMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(hashTagMapper.fromId(null)).isNull();
    }
}
