package com.billcat.web.rest;

import com.billcat.service.IndicatorService;
import com.billcat.web.rest.errors.BadRequestAlertException;
import com.billcat.service.dto.IndicatorDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.billcat.domain.Indicator}.
 */
@RestController
@RequestMapping("/api")
public class IndicatorResource {

    private final Logger log = LoggerFactory.getLogger(IndicatorResource.class);

    private static final String ENTITY_NAME = "indicator";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IndicatorService indicatorService;

    public IndicatorResource(IndicatorService indicatorService) {
        this.indicatorService = indicatorService;
    }

    /**
     * {@code POST  /indicators} : Create a new indicator.
     *
     * @param indicatorDTO the indicatorDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new indicatorDTO, or with status {@code 400 (Bad Request)} if the indicator has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/indicators")
    public ResponseEntity<IndicatorDTO> createIndicator(@RequestBody IndicatorDTO indicatorDTO) throws URISyntaxException {
        log.debug("REST request to save Indicator : {}", indicatorDTO);
        if (indicatorDTO.getId() != null) {
            throw new BadRequestAlertException("A new indicator cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IndicatorDTO result = indicatorService.save(indicatorDTO);
        return ResponseEntity.created(new URI("/api/indicators/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /indicators} : Updates an existing indicator.
     *
     * @param indicatorDTO the indicatorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated indicatorDTO,
     * or with status {@code 400 (Bad Request)} if the indicatorDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the indicatorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/indicators")
    public ResponseEntity<IndicatorDTO> updateIndicator(@RequestBody IndicatorDTO indicatorDTO) throws URISyntaxException {
        log.debug("REST request to update Indicator : {}", indicatorDTO);
        if (indicatorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        IndicatorDTO result = indicatorService.save(indicatorDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, indicatorDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /indicators} : get all the indicators.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of indicators in body.
     */
    @GetMapping("/indicators")
    public List<IndicatorDTO> getAllIndicators() {
        log.debug("REST request to get all Indicators");
        return indicatorService.findAll();
    }

    /**
     * {@code GET  /indicators/:id} : get the "id" indicator.
     *
     * @param id the id of the indicatorDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the indicatorDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/indicators/{id}")
    public ResponseEntity<IndicatorDTO> getIndicator(@PathVariable Long id) {
        log.debug("REST request to get Indicator : {}", id);
        Optional<IndicatorDTO> indicatorDTO = indicatorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(indicatorDTO);
    }

    /**
     * {@code DELETE  /indicators/:id} : delete the "id" indicator.
     *
     * @param id the id of the indicatorDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/indicators/{id}")
    public ResponseEntity<Void> deleteIndicator(@PathVariable Long id) {
        log.debug("REST request to delete Indicator : {}", id);
        indicatorService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
