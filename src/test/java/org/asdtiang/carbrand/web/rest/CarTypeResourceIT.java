package org.asdtiang.carbrand.web.rest;

import org.asdtiang.carbrand.CarbrandApp;
import org.asdtiang.carbrand.domain.CarType;
import org.asdtiang.carbrand.repository.CarTypeRepository;
import org.asdtiang.carbrand.service.CarTypeService;
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
import java.math.BigDecimal;
import java.util.List;

import static org.asdtiang.carbrand.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CarTypeResource} REST controller.
 */
@SpringBootTest(classes = CarbrandApp.class)
public class CarTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_YEAR_NAME = "AAAAAAAAAA";
    private static final String UPDATED_YEAR_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_HAS_PRODUCTION = false;
    private static final Boolean UPDATED_HAS_PRODUCTION = true;

    private static final BigDecimal DEFAULT_PRICE_START = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRICE_START = new BigDecimal(2);

    private static final BigDecimal DEFAULT_PRICE_END = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRICE_END = new BigDecimal(2);

    private static final Boolean DEFAULT_HAS_PRICE = false;
    private static final Boolean UPDATED_HAS_PRICE = true;

    @Autowired
    private CarTypeRepository carTypeRepository;

    @Autowired
    private CarTypeService carTypeService;

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

    private MockMvc restCarTypeMockMvc;

    private CarType carType;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CarTypeResource carTypeResource = new CarTypeResource(carTypeService);
        this.restCarTypeMockMvc = MockMvcBuilders.standaloneSetup(carTypeResource)
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
    public static CarType createEntity(EntityManager em) {
        CarType carType = new CarType()
            .name(DEFAULT_NAME)
            .yearName(DEFAULT_YEAR_NAME)
            .hasProduction(DEFAULT_HAS_PRODUCTION)
            .priceStart(DEFAULT_PRICE_START)
            .priceEnd(DEFAULT_PRICE_END)
            .hasPrice(DEFAULT_HAS_PRICE);
        return carType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CarType createUpdatedEntity(EntityManager em) {
        CarType carType = new CarType()
            .name(UPDATED_NAME)
            .yearName(UPDATED_YEAR_NAME)
            .hasProduction(UPDATED_HAS_PRODUCTION)
            .priceStart(UPDATED_PRICE_START)
            .priceEnd(UPDATED_PRICE_END)
            .hasPrice(UPDATED_HAS_PRICE);
        return carType;
    }

    @BeforeEach
    public void initTest() {
        carType = createEntity(em);
    }

    @Test
    @Transactional
    public void createCarType() throws Exception {
        int databaseSizeBeforeCreate = carTypeRepository.findAll().size();

        // Create the CarType
        restCarTypeMockMvc.perform(post("/api/car-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carType)))
            .andExpect(status().isCreated());

        // Validate the CarType in the database
        List<CarType> carTypeList = carTypeRepository.findAll();
        assertThat(carTypeList).hasSize(databaseSizeBeforeCreate + 1);
        CarType testCarType = carTypeList.get(carTypeList.size() - 1);
        assertThat(testCarType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCarType.getYearName()).isEqualTo(DEFAULT_YEAR_NAME);
        assertThat(testCarType.isHasProduction()).isEqualTo(DEFAULT_HAS_PRODUCTION);
        assertThat(testCarType.getPriceStart()).isEqualTo(DEFAULT_PRICE_START);
        assertThat(testCarType.getPriceEnd()).isEqualTo(DEFAULT_PRICE_END);
        assertThat(testCarType.isHasPrice()).isEqualTo(DEFAULT_HAS_PRICE);
    }

    @Test
    @Transactional
    public void createCarTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = carTypeRepository.findAll().size();

        // Create the CarType with an existing ID
        carType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCarTypeMockMvc.perform(post("/api/car-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carType)))
            .andExpect(status().isBadRequest());

        // Validate the CarType in the database
        List<CarType> carTypeList = carTypeRepository.findAll();
        assertThat(carTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = carTypeRepository.findAll().size();
        // set the field null
        carType.setName(null);

        // Create the CarType, which fails.

        restCarTypeMockMvc.perform(post("/api/car-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carType)))
            .andExpect(status().isBadRequest());

        List<CarType> carTypeList = carTypeRepository.findAll();
        assertThat(carTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkYearNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = carTypeRepository.findAll().size();
        // set the field null
        carType.setYearName(null);

        // Create the CarType, which fails.

        restCarTypeMockMvc.perform(post("/api/car-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carType)))
            .andExpect(status().isBadRequest());

        List<CarType> carTypeList = carTypeRepository.findAll();
        assertThat(carTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHasProductionIsRequired() throws Exception {
        int databaseSizeBeforeTest = carTypeRepository.findAll().size();
        // set the field null
        carType.setHasProduction(null);

        // Create the CarType, which fails.

        restCarTypeMockMvc.perform(post("/api/car-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carType)))
            .andExpect(status().isBadRequest());

        List<CarType> carTypeList = carTypeRepository.findAll();
        assertThat(carTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHasPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = carTypeRepository.findAll().size();
        // set the field null
        carType.setHasPrice(null);

        // Create the CarType, which fails.

        restCarTypeMockMvc.perform(post("/api/car-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carType)))
            .andExpect(status().isBadRequest());

        List<CarType> carTypeList = carTypeRepository.findAll();
        assertThat(carTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCarTypes() throws Exception {
        // Initialize the database
        carTypeRepository.saveAndFlush(carType);

        // Get all the carTypeList
        restCarTypeMockMvc.perform(get("/api/car-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(carType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].yearName").value(hasItem(DEFAULT_YEAR_NAME)))
            .andExpect(jsonPath("$.[*].hasProduction").value(hasItem(DEFAULT_HAS_PRODUCTION.booleanValue())))
            .andExpect(jsonPath("$.[*].priceStart").value(hasItem(DEFAULT_PRICE_START.intValue())))
            .andExpect(jsonPath("$.[*].priceEnd").value(hasItem(DEFAULT_PRICE_END.intValue())))
            .andExpect(jsonPath("$.[*].hasPrice").value(hasItem(DEFAULT_HAS_PRICE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCarType() throws Exception {
        // Initialize the database
        carTypeRepository.saveAndFlush(carType);

        // Get the carType
        restCarTypeMockMvc.perform(get("/api/car-types/{id}", carType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(carType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.yearName").value(DEFAULT_YEAR_NAME))
            .andExpect(jsonPath("$.hasProduction").value(DEFAULT_HAS_PRODUCTION.booleanValue()))
            .andExpect(jsonPath("$.priceStart").value(DEFAULT_PRICE_START.intValue()))
            .andExpect(jsonPath("$.priceEnd").value(DEFAULT_PRICE_END.intValue()))
            .andExpect(jsonPath("$.hasPrice").value(DEFAULT_HAS_PRICE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCarType() throws Exception {
        // Get the carType
        restCarTypeMockMvc.perform(get("/api/car-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCarType() throws Exception {
        // Initialize the database
        carTypeService.save(carType);

        int databaseSizeBeforeUpdate = carTypeRepository.findAll().size();

        // Update the carType
        CarType updatedCarType = carTypeRepository.findById(carType.getId()).get();
        // Disconnect from session so that the updates on updatedCarType are not directly saved in db
        em.detach(updatedCarType);
        updatedCarType
            .name(UPDATED_NAME)
            .yearName(UPDATED_YEAR_NAME)
            .hasProduction(UPDATED_HAS_PRODUCTION)
            .priceStart(UPDATED_PRICE_START)
            .priceEnd(UPDATED_PRICE_END)
            .hasPrice(UPDATED_HAS_PRICE);

        restCarTypeMockMvc.perform(put("/api/car-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCarType)))
            .andExpect(status().isOk());

        // Validate the CarType in the database
        List<CarType> carTypeList = carTypeRepository.findAll();
        assertThat(carTypeList).hasSize(databaseSizeBeforeUpdate);
        CarType testCarType = carTypeList.get(carTypeList.size() - 1);
        assertThat(testCarType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCarType.getYearName()).isEqualTo(UPDATED_YEAR_NAME);
        assertThat(testCarType.isHasProduction()).isEqualTo(UPDATED_HAS_PRODUCTION);
        assertThat(testCarType.getPriceStart()).isEqualTo(UPDATED_PRICE_START);
        assertThat(testCarType.getPriceEnd()).isEqualTo(UPDATED_PRICE_END);
        assertThat(testCarType.isHasPrice()).isEqualTo(UPDATED_HAS_PRICE);
    }

    @Test
    @Transactional
    public void updateNonExistingCarType() throws Exception {
        int databaseSizeBeforeUpdate = carTypeRepository.findAll().size();

        // Create the CarType

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCarTypeMockMvc.perform(put("/api/car-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carType)))
            .andExpect(status().isBadRequest());

        // Validate the CarType in the database
        List<CarType> carTypeList = carTypeRepository.findAll();
        assertThat(carTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCarType() throws Exception {
        // Initialize the database
        carTypeService.save(carType);

        int databaseSizeBeforeDelete = carTypeRepository.findAll().size();

        // Delete the carType
        restCarTypeMockMvc.perform(delete("/api/car-types/{id}", carType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CarType> carTypeList = carTypeRepository.findAll();
        assertThat(carTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
