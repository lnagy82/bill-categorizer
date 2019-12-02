package com.billcat.web.rest;

import com.billcat.BillcategorizerApp;
import com.billcat.domain.AccountItem;
import com.billcat.repository.AccountItemRepository;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.billcat.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.billcat.domain.enumeration.Category;
/**
 * Integration tests for the {@link AccountItemResource} REST controller.
 */
@SpringBootTest(classes = BillcategorizerApp.class)
public class AccountItemResourceIT {

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_TRANSACTION_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TRANSACTION_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Double DEFAULT_AMOUNT = 1D;
    private static final Double UPDATED_AMOUNT = 2D;

    private static final String DEFAULT_CURRENCY = "AAAAAAAAAA";
    private static final String UPDATED_CURRENCY = "BBBBBBBBBB";

    private static final Category DEFAULT_CATEGORY = Category.FOOD;
    private static final Category UPDATED_CATEGORY = Category.FOOD;

    @Autowired
    private AccountItemRepository accountItemRepository;

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

    private MockMvc restAccountItemMockMvc;

    private AccountItem accountItem;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AccountItemResource accountItemResource = new AccountItemResource(accountItemRepository);
        this.restAccountItemMockMvc = MockMvcBuilders.standaloneSetup(accountItemResource)
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
    public static AccountItem createEntity(EntityManager em) {
        AccountItem accountItem = new AccountItem()
            .date(DEFAULT_DATE)
            .transactionType(DEFAULT_TRANSACTION_TYPE)
            .description(DEFAULT_DESCRIPTION)
            .amount(DEFAULT_AMOUNT)
            .currency(DEFAULT_CURRENCY)
            .category(DEFAULT_CATEGORY);
        return accountItem;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AccountItem createUpdatedEntity(EntityManager em) {
        AccountItem accountItem = new AccountItem()
            .date(UPDATED_DATE)
            .transactionType(UPDATED_TRANSACTION_TYPE)
            .description(UPDATED_DESCRIPTION)
            .amount(UPDATED_AMOUNT)
            .currency(UPDATED_CURRENCY)
            .category(UPDATED_CATEGORY);
        return accountItem;
    }

    @BeforeEach
    public void initTest() {
        accountItem = createEntity(em);
    }

    @Test
    @Transactional
    public void createAccountItem() throws Exception {
        int databaseSizeBeforeCreate = accountItemRepository.findAll().size();

        // Create the AccountItem
        restAccountItemMockMvc.perform(post("/api/account-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accountItem)))
            .andExpect(status().isCreated());

        // Validate the AccountItem in the database
        List<AccountItem> accountItemList = accountItemRepository.findAll();
        assertThat(accountItemList).hasSize(databaseSizeBeforeCreate + 1);
        AccountItem testAccountItem = accountItemList.get(accountItemList.size() - 1);
        assertThat(testAccountItem.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testAccountItem.getTransactionType()).isEqualTo(DEFAULT_TRANSACTION_TYPE);
        assertThat(testAccountItem.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testAccountItem.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testAccountItem.getCurrency()).isEqualTo(DEFAULT_CURRENCY);
        assertThat(testAccountItem.getCategory()).isEqualTo(DEFAULT_CATEGORY);
    }

    @Test
    @Transactional
    public void createAccountItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = accountItemRepository.findAll().size();

        // Create the AccountItem with an existing ID
        accountItem.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAccountItemMockMvc.perform(post("/api/account-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accountItem)))
            .andExpect(status().isBadRequest());

        // Validate the AccountItem in the database
        List<AccountItem> accountItemList = accountItemRepository.findAll();
        assertThat(accountItemList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAccountItems() throws Exception {
        // Initialize the database
        accountItemRepository.saveAndFlush(accountItem);

        // Get all the accountItemList
        restAccountItemMockMvc.perform(get("/api/account-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(accountItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].transactionType").value(hasItem(DEFAULT_TRANSACTION_TYPE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].currency").value(hasItem(DEFAULT_CURRENCY)))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY.toString())));
    }
    
    @Test
    @Transactional
    public void getAccountItem() throws Exception {
        // Initialize the database
        accountItemRepository.saveAndFlush(accountItem);

        // Get the accountItem
        restAccountItemMockMvc.perform(get("/api/account-items/{id}", accountItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(accountItem.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.transactionType").value(DEFAULT_TRANSACTION_TYPE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.currency").value(DEFAULT_CURRENCY))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAccountItem() throws Exception {
        // Get the accountItem
        restAccountItemMockMvc.perform(get("/api/account-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAccountItem() throws Exception {
        // Initialize the database
        accountItemRepository.saveAndFlush(accountItem);

        int databaseSizeBeforeUpdate = accountItemRepository.findAll().size();

        // Update the accountItem
        AccountItem updatedAccountItem = accountItemRepository.findById(accountItem.getId()).get();
        // Disconnect from session so that the updates on updatedAccountItem are not directly saved in db
        em.detach(updatedAccountItem);
        updatedAccountItem
            .date(UPDATED_DATE)
            .transactionType(UPDATED_TRANSACTION_TYPE)
            .description(UPDATED_DESCRIPTION)
            .amount(UPDATED_AMOUNT)
            .currency(UPDATED_CURRENCY)
            .category(UPDATED_CATEGORY);

        restAccountItemMockMvc.perform(put("/api/account-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAccountItem)))
            .andExpect(status().isOk());

        // Validate the AccountItem in the database
        List<AccountItem> accountItemList = accountItemRepository.findAll();
        assertThat(accountItemList).hasSize(databaseSizeBeforeUpdate);
        AccountItem testAccountItem = accountItemList.get(accountItemList.size() - 1);
        assertThat(testAccountItem.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testAccountItem.getTransactionType()).isEqualTo(UPDATED_TRANSACTION_TYPE);
        assertThat(testAccountItem.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAccountItem.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testAccountItem.getCurrency()).isEqualTo(UPDATED_CURRENCY);
        assertThat(testAccountItem.getCategory()).isEqualTo(UPDATED_CATEGORY);
    }

    @Test
    @Transactional
    public void updateNonExistingAccountItem() throws Exception {
        int databaseSizeBeforeUpdate = accountItemRepository.findAll().size();

        // Create the AccountItem

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAccountItemMockMvc.perform(put("/api/account-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accountItem)))
            .andExpect(status().isBadRequest());

        // Validate the AccountItem in the database
        List<AccountItem> accountItemList = accountItemRepository.findAll();
        assertThat(accountItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAccountItem() throws Exception {
        // Initialize the database
        accountItemRepository.saveAndFlush(accountItem);

        int databaseSizeBeforeDelete = accountItemRepository.findAll().size();

        // Delete the accountItem
        restAccountItemMockMvc.perform(delete("/api/account-items/{id}", accountItem.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AccountItem> accountItemList = accountItemRepository.findAll();
        assertThat(accountItemList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
