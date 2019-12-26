package com.billcat.web.rest;

import com.billcat.security.AuthoritiesConstants;
import com.billcat.service.AccountItemService;
import com.billcat.web.rest.errors.BadRequestAlertException;
import com.billcat.service.dto.AccountItemDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
public class AccountItemResource {

    private final Logger log = LoggerFactory.getLogger(AccountItemResource.class);

    private static final String ENTITY_NAME = "accountItem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AccountItemService accountItemService;

    public AccountItemResource(AccountItemService accountItemService) {
        this.accountItemService = accountItemService;
    }

    /**
     * {@code POST  /account-items} : Create a new accountItem.
     *
     * @param accountItemDTO the accountItemDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new accountItemDTO, or with status {@code 400 (Bad Request)} if the accountItem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/account-items")
    public ResponseEntity<AccountItemDTO> createAccountItem(@RequestBody AccountItemDTO accountItemDTO) throws URISyntaxException {
        log.debug("REST request to save AccountItem : {}", accountItemDTO);
        if (accountItemDTO.getId() != null) {
            throw new BadRequestAlertException("A new accountItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AccountItemDTO result = accountItemService.save(accountItemDTO);
        return ResponseEntity.created(new URI("/api/account-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /account-items} : Updates an existing accountItem.
     *
     * @param accountItemDTO the accountItemDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated accountItemDTO,
     * or with status {@code 400 (Bad Request)} if the accountItemDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the accountItemDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/account-items")
    public ResponseEntity<AccountItemDTO> updateAccountItem(@RequestBody AccountItemDTO accountItemDTO) throws URISyntaxException {
        log.debug("REST request to update AccountItem : {}", accountItemDTO);
        if (accountItemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AccountItemDTO result = accountItemService.save(accountItemDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, accountItemDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /account-items} : get all the accountItems.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of accountItems in body.
     */
    @GetMapping("/account-items")
    public ResponseEntity<List<AccountItemDTO>> getAllAccountItems(Pageable pageable) {
        log.debug("REST request to get a page of AccountItems");
        Page<AccountItemDTO> page = accountItemService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }


    /**
     * {@code GET  /account-items/:id} : get the "id" accountItem.
     *
     * @param id the id of the accountItemDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the accountItemDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/account-items/{id}")
    public ResponseEntity<AccountItemDTO> getAccountItem(@PathVariable Long id) {
        log.debug("REST request to get AccountItem : {}", id);
        Optional<AccountItemDTO> accountItemDTO = accountItemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(accountItemDTO);
    }

    /**
     * {@code DELETE  /account-items/:id} : delete the "id" accountItem.
     *
     * @param id the id of the accountItemDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/account-items/{id}")
    public ResponseEntity<Void> deleteAccountItem(@PathVariable Long id) {
        log.debug("REST request to delete AccountItem : {}", id);
        accountItemService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
    
    /**
     * {@code GET  /account-items} : get all the accountItems.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of accountItems in body.
     */
    @GetMapping("/account-items/load")
    public ResponseEntity<Void> getLoadAccountItems() {
        log.debug("********* LOAD ************");
        accountItemService.load();
        
        return ResponseEntity.noContent().build();
    }
}
