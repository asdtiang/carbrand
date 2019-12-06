package org.asdtiang.carbrand.web.rest;

import org.asdtiang.carbrand.CarbrandApp;
import org.asdtiang.carbrand.domain.BrandType;
import org.asdtiang.carbrand.repository.BrandTypeRepository;
import org.asdtiang.carbrand.service.BrandTypeService;
import org.asdtiang.carbrand.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static org.asdtiang.carbrand.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link BrandTypeResource} REST controller.
 */
@SpringBootTest(classes = CarbrandApp.class)
public class BrandTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private BrandTypeRepository brandTypeRepository;

    @Autowired
    private BrandTypeService brandTypeService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restBrandTypeMockMvc;

    private BrandType brandType;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BrandTypeResource brandTypeResource = new BrandTypeResource(brandTypeService);
        this.restBrandTypeMockMvc = MockMvcBuilders.standaloneSetup(brandTypeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BrandType createEntity(EntityManager em) {
        BrandType brandType = new BrandType()
            .name(DEFAULT_NAME);
        return brandType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BrandType createUpdatedEntity(EntityManager em) {
        BrandType brandType = new BrandType()
            .name(UPDATED_NAME);
        return brandType;
    }

    @BeforeEach
    public void initTest() {
        brandType = createEntity(em);
    }

    @Test
    @Transactional
    public void createBrandType() throws Exception {
        int databaseSizeBeforeCreate = brandTypeRepository.findAll().size();

        // Create the BrandType
        restBrandTypeMockMvc.perform(post("/api/brand-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(brandType)))
            .andExpect(status().isCreated());

        // Validate the BrandType in the database
        List<BrandType> brandTypeList = brandTypeRepository.findAll();
        assertThat(brandTypeList).hasSize(databaseSizeBeforeCreate + 1);
        BrandType testBrandType = brandTypeList.get(brandTypeList.size() - 1);
        assertThat(testBrandType.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createBrandTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = brandTypeRepository.findAll().size();

        // Create the BrandType with an existing ID
        brandType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBrandTypeMockMvc.perform(post("/api/brand-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(brandType)))
            .andExpect(status().isBadRequest());

        // Validate the BrandType in the database
        List<BrandType> brandTypeList = brandTypeRepository.findAll();
        assertThat(brandTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = brandTypeRepository.findAll().size();
        // set the field null
        brandType.setName(null);

        // Create the BrandType, which fails.

        restBrandTypeMockMvc.perform(post("/api/brand-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(brandType)))
            .andExpect(status().isBadRequest());

        List<BrandType> brandTypeList = brandTypeRepository.findAll();
        assertThat(brandTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBrandTypes() throws Exception {
        // Initialize the database
        brandTypeRepository.saveAndFlush(brandType);

        // Get all the brandTypeList
        restBrandTypeMockMvc.perform(get("/api/brand-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(brandType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getBrandType() throws Exception {
        // Initialize the database
        brandTypeRepository.saveAndFlush(brandType);

        // Get the brandType
        restBrandTypeMockMvc.perform(get("/api/brand-types/{id}", brandType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(brandType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    public void getNonExistingBrandType() throws Exception {
        // Get the brandType
        restBrandTypeMockMvc.perform(get("/api/brand-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBrandType() throws Exception {
        // Initialize the database
        brandTypeService.save(brandType);

        int databaseSizeBeforeUpdate = brandTypeRepository.findAll().size();

        // Update the brandType
        BrandType updatedBrandType = brandTypeRepository.findById(brandType.getId()).get();
        // Disconnect from session so that the updates on updatedBrandType are not directly saved in db
        em.detach(updatedBrandType);
        updatedBrandType
            .name(UPDATED_NAME);

        restBrandTypeMockMvc.perform(put("/api/brand-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBrandType)))
            .andExpect(status().isOk());

        // Validate the BrandType in the database
        List<BrandType> brandTypeList = brandTypeRepository.findAll();
        assertThat(brandTypeList).hasSize(databaseSizeBeforeUpdate);
        BrandType testBrandType = brandTypeList.get(brandTypeList.size() - 1);
        assertThat(testBrandType.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingBrandType() throws Exception {
        int databaseSizeBeforeUpdate = brandTypeRepository.findAll().size();

        // Create the BrandType

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBrandTypeMockMvc.perform(put("/api/brand-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(brandType)))
            .andExpect(status().isBadRequest());

        // Validate the BrandType in the database
        List<BrandType> brandTypeList = brandTypeRepository.findAll();
        assertThat(brandTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBrandType() throws Exception {
        // Initialize the database
        brandTypeService.save(brandType);

        int databaseSizeBeforeDelete = brandTypeRepository.findAll().size();

        // Delete the brandType
        restBrandTypeMockMvc.perform(delete("/api/brand-types/{id}", brandType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BrandType> brandTypeList = brandTypeRepository.findAll();
        assertThat(brandTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
