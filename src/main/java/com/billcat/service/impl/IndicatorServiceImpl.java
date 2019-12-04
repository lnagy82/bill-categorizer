package com.billcat.service.impl;

import com.billcat.service.IndicatorService;
import com.billcat.domain.Indicator;
import com.billcat.repository.IndicatorRepository;
import com.billcat.service.dto.IndicatorDTO;
import com.billcat.service.mapper.IndicatorMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Indicator}.
 */
@Service
@Transactional
public class IndicatorServiceImpl implements IndicatorService {

    private final Logger log = LoggerFactory.getLogger(IndicatorServiceImpl.class);

    private final IndicatorRepository indicatorRepository;

    private final IndicatorMapper indicatorMapper;

    public IndicatorServiceImpl(IndicatorRepository indicatorRepository, IndicatorMapper indicatorMapper) {
        this.indicatorRepository = indicatorRepository;
        this.indicatorMapper = indicatorMapper;
    }

    /**
     * Save a indicator.
     *
     * @param indicatorDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public IndicatorDTO save(IndicatorDTO indicatorDTO) {
        log.debug("Request to save Indicator : {}", indicatorDTO);
        Indicator indicator = indicatorMapper.toEntity(indicatorDTO);
        indicator = indicatorRepository.save(indicator);
        return indicatorMapper.toDto(indicator);
    }

    /**
     * Get all the indicators.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<IndicatorDTO> findAll() {
        log.debug("Request to get all Indicators");
        return indicatorRepository.findAll().stream()
            .map(indicatorMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one indicator by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<IndicatorDTO> findOne(Long id) {
        log.debug("Request to get Indicator : {}", id);
        return indicatorRepository.findById(id)
            .map(indicatorMapper::toDto);
    }

    /**
     * Delete the indicator by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Indicator : {}", id);
        indicatorRepository.deleteById(id);
    }
}
