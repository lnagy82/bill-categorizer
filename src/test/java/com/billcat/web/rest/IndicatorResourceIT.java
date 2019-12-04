package com.billcat.web.rest;

import com.billcat.BillcategorizerApp;
import com.billcat.domain.Indicator;
import com.billcat.repository.IndicatorRepository;
import com.billcat.service.IndicatorService;
import com.billcat.service.dto.IndicatorDTO;
import com.billcat.service.mapper.IndicatorMapper;
import com.billcat.web.rest.errors.ExceptionTranslator;

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

import static com.billcat.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link IndicatorResource} REST controller.
 */
@SpringBootTest(classes = BillcategorizerApp.class)
public class IndicatorResourceIT {

    private static final String DEFAULT_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_TEXT = "BBBBBBBBBB";

    @Autowired
    private IndicatorRepository indicatorRepository;

    @Autowired
    private IndicatorMapper indicatorMapper;

    @Autowired
    private IndicatorService indicatorService;

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

    private MockMvc restIndicatorMockMvc;

    private Indicator indicator;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final IndicatorResource indicatorResource = new IndicatorResource(indicatorService);
        this.restIndicatorMockMvc = MockMvcBuilders.standaloneSetup(indicatorResource)
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
    public static Indicator createEntity(EntityManager em) {
        Indicator indicator = new Indicator()
            .text(DEFAULT_TEXT);
        return indicator;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Indicator createUpdatedEntity(EntityManager em) {
        Indicator indicator = new Indicator()
            .text(UPDATED_TEXT);
        return indicator;
    }

    @BeforeEach
    public void initTest() {
        indicator = createEntity(em);
    }

    @Test
    @Transactional
    public void createIndicator() throws Exception {
        int databaseSizeBeforeCreate = indicatorRepository.findAll().size();

        // Create the Indicator
        IndicatorDTO indicatorDTO = indicatorMapper.toDto(indicator);
        restIndicatorMockMvc.perform(post("/api/indicators")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(indicatorDTO)))
            .andExpect(status().isCreated());

        // Validate the Indicator in the database
        List<Indicator> indicatorList = indicatorRepository.findAll();
        assertThat(indicatorList).hasSize(databaseSizeBeforeCreate + 1);
        Indicator testIndicator = indicatorList.get(indicatorList.size() - 1);
        assertThat(testIndicator.getText()).isEqualTo(DEFAULT_TEXT);
    }

    @Test
    @Transactional
    public void createIndicatorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = indicatorRepository.findAll().size();

        // Create the Indicator with an existing ID
        indicator.setId(1L);
        IndicatorDTO indicatorDTO = indicatorMapper.toDto(indicator);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIndicatorMockMvc.perform(post("/api/indicators")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(indicatorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Indicator in the database
        List<Indicator> indicatorList = indicatorRepository.findAll();
        assertThat(indicatorList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllIndicators() throws Exception {
        // Initialize the database
        indicatorRepository.saveAndFlush(indicator);

        // Get all the indicatorList
        restIndicatorMockMvc.perform(get("/api/indicators?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(indicator.getId().intValue())))
            .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT)));
    }
    
    @Test
    @Transactional
    public void getIndicator() throws Exception {
        // Initialize the database
        indicatorRepository.saveAndFlush(indicator);

        // Get the indicator
        restIndicatorMockMvc.perform(get("/api/indicators/{id}", indicator.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(indicator.getId().intValue()))
            .andExpect(jsonPath("$.text").value(DEFAULT_TEXT));
    }

    @Test
    @Transactional
    public void getNonExistingIndicator() throws Exception {
        // Get the indicator
        restIndicatorMockMvc.perform(get("/api/indicators/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIndicator() throws Exception {
        // Initialize the database
        indicatorRepository.saveAndFlush(indicator);

        int databaseSizeBeforeUpdate = indicatorRepository.findAll().size();

        // Update the indicator
        Indicator updatedIndicator = indicatorRepository.findById(indicator.getId()).get();
        // Disconnect from session so that the updates on updatedIndicator are not directly saved in db
        em.detach(updatedIndicator);
        updatedIndicator
            .text(UPDATED_TEXT);
        IndicatorDTO indicatorDTO = indicatorMapper.toDto(updatedIndicator);

        restIndicatorMockMvc.perform(put("/api/indicators")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(indicatorDTO)))
            .andExpect(status().isOk());

        // Validate the Indicator in the database
        List<Indicator> indicatorList = indicatorRepository.findAll();
        assertThat(indicatorList).hasSize(databaseSizeBeforeUpdate);
        Indicator testIndicator = indicatorList.get(indicatorList.size() - 1);
        assertThat(testIndicator.getText()).isEqualTo(UPDATED_TEXT);
    }

    @Test
    @Transactional
    public void updateNonExistingIndicator() throws Exception {
        int databaseSizeBeforeUpdate = indicatorRepository.findAll().size();

        // Create the Indicator
        IndicatorDTO indicatorDTO = indicatorMapper.toDto(indicator);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIndicatorMockMvc.perform(put("/api/indicators")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(indicatorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Indicator in the database
        List<Indicator> indicatorList = indicatorRepository.findAll();
        assertThat(indicatorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteIndicator() throws Exception {
        // Initialize the database
        indicatorRepository.saveAndFlush(indicator);

        int databaseSizeBeforeDelete = indicatorRepository.findAll().size();

        // Delete the indicator
        restIndicatorMockMvc.perform(delete("/api/indicators/{id}", indicator.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Indicator> indicatorList = indicatorRepository.findAll();
        assertThat(indicatorList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
