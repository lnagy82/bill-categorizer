package com.billcat.service.mapper;

import com.billcat.domain.*;
import com.billcat.service.dto.IndicatorDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Indicator} and its DTO {@link IndicatorDTO}.
 */
@Mapper(componentModel = "spring", uses = {CategoryMapper.class})
public interface IndicatorMapper extends EntityMapper<IndicatorDTO, Indicator> {

    @Mapping(source = "category.id", target = "categoryId")
    IndicatorDTO toDto(Indicator indicator);

    @Mapping(source = "categoryId", target = "category")
    Indicator toEntity(IndicatorDTO indicatorDTO);

    default Indicator fromId(Long id) {
        if (id == null) {
            return null;
        }
        Indicator indicator = new Indicator();
        indicator.setId(id);
        return indicator;
    }
}
