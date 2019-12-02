package com.billcat.service;

import com.billcat.service.dto.AccountItemDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.billcat.domain.AccountItem}.
 */
public interface AccountItemService {

    /**
     * Save a accountItem.
     *
     * @param accountItemDTO the entity to save.
     * @return the persisted entity.
     */
    AccountItemDTO save(AccountItemDTO accountItemDTO);

    /**
     * Get all the accountItems.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AccountItemDTO> findAll(Pageable pageable);


    /**
     * Get the "id" accountItem.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AccountItemDTO> findOne(Long id);

    /**
     * Delete the "id" accountItem.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
