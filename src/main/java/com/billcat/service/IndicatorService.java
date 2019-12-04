package com.billcat.service;

import com.billcat.service.dto.IndicatorDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.billcat.domain.Indicator}.
 */
public interface IndicatorService {

    /**
     * Save a indicator.
     *
     * @param indicatorDTO the entity to save.
     * @return the persisted entity.
     */
    IndicatorDTO save(IndicatorDTO indicatorDTO);

    /**
     * Get all the indicators.
     *
     * @return the list of entities.
     */
    List<IndicatorDTO> findAll();


    /**
     * Get the "id" indicator.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<IndicatorDTO> findOne(Long id);

    /**
     * Delete the "id" indicator.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
