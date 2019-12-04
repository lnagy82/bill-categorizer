package com.billcat.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class IndicatorMapperTest {

    private IndicatorMapper indicatorMapper;

    @BeforeEach
    public void setUp() {
        indicatorMapper = new IndicatorMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(indicatorMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(indicatorMapper.fromId(null)).isNull();
    }
}
