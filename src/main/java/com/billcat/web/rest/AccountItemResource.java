package com.billcat.web.rest;

import com.billcat.domain.AccountItem;
import com.billcat.repository.AccountItemRepository;
import com.billcat.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional; 
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.billcat.domain.AccountItem}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class AccountItemResource {

    private final Logger log = LoggerFactory.getLogger(AccountItemResource.class);

    private static final String ENTITY_NAME = "accountItem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AccountItemRepository accountItemRepository;

    public AccountItemResource(AccountItemRepository accountItemRepository) {
        this.accountItemRepository = accountItemRepository;
    }

    /**
     * {@code POST  /account-items} : Create a new accountItem.
     *
     * @param accountItem the accountItem to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new accountItem, or with status {@code 400 (Bad Request)} if the accountItem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/account-items")
    public ResponseEntity<AccountItem> createAccountItem(@RequestBody AccountItem accountItem) throws URISyntaxException {
        log.debug("REST request to save AccountItem : {}", accountItem);
        if (accountItem.getId() != null) {
            throw new BadRequestAlertException("A new accountItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AccountItem result = accountItemRepository.save(accountItem);
        return ResponseEntity.created(new URI("/api/account-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /account-items} : Updates an existing accountItem.
     *
     * @param accountItem the accountItem to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated accountItem,
     * or with status {@code 400 (Bad Request)} if the accountItem is not valid,
     * or with status {@code 500 (Internal Server Error)} if the accountItem couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/account-items")
    public ResponseEntity<AccountItem> updateAccountItem(@RequestBody AccountItem accountItem) throws URISyntaxException {
        log.debug("REST request to update AccountItem : {}", accountItem);
        if (accountItem.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AccountItem result = accountItemRepository.save(accountItem);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, accountItem.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /account-items} : get all the accountItems.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of accountItems in body.
     */
    @GetMapping("/account-items")
    public List<AccountItem> getAllAccountItems() {
        log.debug("REST request to get all AccountItems");
        return accountItemRepository.findAll();
    }

    /**
     * {@code GET  /account-items/:id} : get the "id" accountItem.
     *
     * @param id the id of the accountItem to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the accountItem, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/account-items/{id}")
    public ResponseEntity<AccountItem> getAccountItem(@PathVariable Long id) {
        log.debug("REST request to get AccountItem : {}", id);
        Optional<AccountItem> accountItem = accountItemRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(accountItem);
    }

    /**
     * {@code DELETE  /account-items/:id} : delete the "id" accountItem.
     *
     * @param id the id of the accountItem to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/account-items/{id}")
    public ResponseEntity<Void> deleteAccountItem(@PathVariable Long id) {
        log.debug("REST request to delete AccountItem : {}", id);
        accountItemRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
