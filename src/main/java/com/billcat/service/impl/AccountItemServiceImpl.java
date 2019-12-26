package com.billcat.service.impl;

import com.billcat.service.AccountItemService;
import com.billcat.domain.AccountItem;
import com.billcat.domain.Indicator;
import com.billcat.repository.AccountItemRepository;
import com.billcat.repository.IndicatorRepository;
import com.billcat.service.dto.AccountItemDTO;
import com.billcat.service.mapper.AccountItemMapper;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.apache.commons.lang3.time.FastDateParser;
import org.apache.poi.hssf.usermodel.HSSFDataFormatter;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TimeZone;

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
        return accountItemRepository.findAll(pageable).map(accountItemMapper::toDto);
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
        return accountItemRepository.findById(id).map(accountItemMapper::toDto);
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

    @Override
    public void load() {
        System.out.println("bigyó");

        FileInputStream file;
        try {
            file = new FileInputStream(new File("d:\\temp\\SzámlaMozgások0008EH2UW3511.xls"));
            Workbook workbook = new HSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0);
            List<Indicator> indicators = 
            for (Row row : sheet) {
                AccountItem accountItem = new AccountItem();
                try {
                    if (row.getCell(0) == null)
                        continue;
                    accountItem.setDate(FastDateFormat.getInstance("MM/dd/yy", TimeZone.getTimeZone("GMT"))
                            .parse(getCellValueAsString(row.getCell(0))).toInstant());
                    accountItem.setTransactionType(row.getCell(1).toString());
                    accountItem.setDescription(row.getCell(2).toString());
                    accountItem.setAmount(Double.valueOf(row.getCell(3).toString()));
                    accountItem.setCurrency(row.getCell(4).toString());
                    
                    Example<AccountItem> accountItemExample = Example.of(accountItem, ExampleMatcher.matchingAll());
                    
                    if(accountItemRepository.findAll(accountItemExample).isEmpty())
                        accountItemRepository.save(accountItem);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return null;
        } else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
            return cell.toString();
        } else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            DataFormatter formatter = new HSSFDataFormatter();
            String formattedValue = formatter.formatCellValue(cell);
            return formattedValue;
        } else {
            System.out.println("Error izé.");
        }
        return null;
    }
}
