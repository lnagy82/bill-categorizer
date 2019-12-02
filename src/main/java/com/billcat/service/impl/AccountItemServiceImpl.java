package com.billcat.service.impl;

import com.billcat.service.AccountItemService;
import com.billcat.domain.AccountItem;
import com.billcat.repository.AccountItemRepository;
import com.billcat.service.dto.AccountItemDTO;
import com.billcat.service.mapper.AccountItemMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link AccountItem}.
 */
@Service
@Transactional
public class AccountItemServiceImpl implements AccountItemService {

    private final Logger log = LoggerFactory.getLogger(AccountItemServiceImpl.class);

    private final AccountItemRepository accountItemRepository;

    private final AccountItemMapper accountItemMapper;

    public AccountItemServiceImpl(AccountItemRepository accountItemRepository, AccountItemMapper accountItemMapper) {
        this.accountItemRepository = accountItemRepository;
        this.accountItemMapper = accountItemMapper;
    }

    /**
     * Save a accountItem.
     *
     * @param accountItemDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public AccountItemDTO save(AccountItemDTO accountItemDTO) {
        log.debug("Request to save AccountItem : {}", accountItemDTO);
        AccountItem accountItem = accountItemMapper.toEntity(accountItemDTO);
        accountItem = accountItemRepository.save(accountItem);
        return accountItemMapper.toDto(accountItem);
    }

    /**
     * Get all the accountItems.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AccountItemDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AccountItems");
        return accountItemRepository.findAll(pageable)
            .map(accountItemMapper::toDto);
    }


    /**
     * Get one accountItem by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AccountItemDTO> findOne(Long id) {
        log.debug("Request to get AccountItem : {}", id);
        return accountItemRepository.findById(id)
            .map(accountItemMapper::toDto);
    }

    /**
     * Delete the accountItem by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AccountItem : {}", id);
        accountItemRepository.deleteById(id);
    }
}
