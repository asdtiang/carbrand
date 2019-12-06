package org.asdtiang.carbrand.web.rest;

import org.asdtiang.carbrand.CarbrandApp;
import org.asdtiang.carbrand.domain.Series;
import org.asdtiang.carbrand.repository.SeriesRepository;
import org.asdtiang.carbrand.service.SeriesService;
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
 * Integration tests for the {@link SeriesResource} REST controller.
 */
@SpringBootTest(classes = CarbrandApp.class)
public class SeriesResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_PRICE_START = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRICE_START = new BigDecimal(2);

    private static final BigDecimal DEFAULT_PRICE_END = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRICE_END = new BigDecimal(2);

    private static final Boolean DEFAULT_HAS_PRICE = false;
    private static final Boolean UPDATED_HAS_PRICE = true;

    @Autowired
    private SeriesRepository seriesRepository;

    @Autowired
    private SeriesService seriesService;

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

    private MockMvc restSeriesMockMvc;

    private Series series;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SeriesResource seriesResource = new SeriesResource(seriesService);
        this.restSeriesMockMvc = MockMvcBuilders.standaloneSetup(seriesResource)
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
    public static Series createEntity(EntityManager em) {
        Series series = new Series()
            .name(DEFAULT_NAME)
            .priceStart(DEFAULT_PRICE_START)
            .priceEnd(DEFAULT_PRICE_END)
            .hasPrice(DEFAULT_HAS_PRICE);
        return series;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Series createUpdatedEntity(EntityManager em) {
        Series series = new Series()
            .name(UPDATED_NAME)
            .priceStart(UPDATED_PRICE_START)
            .priceEnd(UPDATED_PRICE_END)
            .hasPrice(UPDATED_HAS_PRICE);
        return series;
    }

    @BeforeEach
    public void initTest() {
        series = createEntity(em);
    }

    @Test
    @Transactional
    public void createSeries() throws Exception {
        int databaseSizeBeforeCreate = seriesRepository.findAll().size();

        // Create the Series
        restSeriesMockMvc.perform(post("/api/series")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(series)))
            .andExpect(status().isCreated());

        // Validate the Series in the database
        List<Series> seriesList = seriesRepository.findAll();
        assertThat(seriesList).hasSize(databaseSizeBeforeCreate + 1);
        Series testSeries = seriesList.get(seriesList.size() - 1);
        assertThat(testSeries.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSeries.getPriceStart()).isEqualTo(DEFAULT_PRICE_START);
        assertThat(testSeries.getPriceEnd()).isEqualTo(DEFAULT_PRICE_END);
        assertThat(testSeries.isHasPrice()).isEqualTo(DEFAULT_HAS_PRICE);
    }

    @Test
    @Transactional
    public void createSeriesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = seriesRepository.findAll().size();

        // Create the Series with an existing ID
        series.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSeriesMockMvc.perform(post("/api/series")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(series)))
            .andExpect(status().isBadRequest());

        // Validate the Series in the database
        List<Series> seriesList = seriesRepository.findAll();
        assertThat(seriesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = seriesRepository.findAll().size();
        // set the field null
        series.setName(null);

        // Create the Series, which fails.

        restSeriesMockMvc.perform(post("/api/series")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(series)))
            .andExpect(status().isBadRequest());

        List<Series> seriesList = seriesRepository.findAll();
        assertThat(seriesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHasPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = seriesRepository.findAll().size();
        // set the field null
        series.setHasPrice(null);

        // Create the Series, which fails.

        restSeriesMockMvc.perform(post("/api/series")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(series)))
            .andExpect(status().isBadRequest());

        List<Series> seriesList = seriesRepository.findAll();
        assertThat(seriesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSeries() throws Exception {
        // Initialize the database
        seriesRepository.saveAndFlush(series);

        // Get all the seriesList
        restSeriesMockMvc.perform(get("/api/series?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(series.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].priceStart").value(hasItem(DEFAULT_PRICE_START.intValue())))
            .andExpect(jsonPath("$.[*].priceEnd").value(hasItem(DEFAULT_PRICE_END.intValue())))
            .andExpect(jsonPath("$.[*].hasPrice").value(hasItem(DEFAULT_HAS_PRICE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getSeries() throws Exception {
        // Initialize the database
        seriesRepository.saveAndFlush(series);

        // Get the series
        restSeriesMockMvc.perform(get("/api/series/{id}", series.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(series.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.priceStart").value(DEFAULT_PRICE_START.intValue()))
            .andExpect(jsonPath("$.priceEnd").value(DEFAULT_PRICE_END.intValue()))
            .andExpect(jsonPath("$.hasPrice").value(DEFAULT_HAS_PRICE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingSeries() throws Exception {
        // Get the series
        restSeriesMockMvc.perform(get("/api/series/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSeries() throws Exception {
        // Initialize the database
        seriesService.save(series);

        int databaseSizeBeforeUpdate = seriesRepository.findAll().size();

        // Update the series
        Series updatedSeries = seriesRepository.findById(series.getId()).get();
        // Disconnect from session so that the updates on updatedSeries are not directly saved in db
        em.detach(updatedSeries);
        updatedSeries
            .name(UPDATED_NAME)
            .priceStart(UPDATED_PRICE_START)
            .priceEnd(UPDATED_PRICE_END)
            .hasPrice(UPDATED_HAS_PRICE);

        restSeriesMockMvc.perform(put("/api/series")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSeries)))
            .andExpect(status().isOk());

        // Validate the Series in the database
        List<Series> seriesList = seriesRepository.findAll();
        assertThat(seriesList).hasSize(databaseSizeBeforeUpdate);
        Series testSeries = seriesList.get(seriesList.size() - 1);
        assertThat(testSeries.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSeries.getPriceStart()).isEqualTo(UPDATED_PRICE_START);
        assertThat(testSeries.getPriceEnd()).isEqualTo(UPDATED_PRICE_END);
        assertThat(testSeries.isHasPrice()).isEqualTo(UPDATED_HAS_PRICE);
    }

    @Test
    @Transactional
    public void updateNonExistingSeries() throws Exception {
        int databaseSizeBeforeUpdate = seriesRepository.findAll().size();

        // Create the Series

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSeriesMockMvc.perform(put("/api/series")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(series)))
            .andExpect(status().isBadRequest());

        // Validate the Series in the database
        List<Series> seriesList = seriesRepository.findAll();
        assertThat(seriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSeries() throws Exception {
        // Initialize the database
        seriesService.save(series);

        int databaseSizeBeforeDelete = seriesRepository.findAll().size();

        // Delete the series
        restSeriesMockMvc.perform(delete("/api/series/{id}", series.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Series> seriesList = seriesRepository.findAll();
        assertThat(seriesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
