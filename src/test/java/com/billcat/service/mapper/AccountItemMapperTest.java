package com.billcat.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class AccountItemMapperTest {

    private AccountItemMapper accountItemMapper;

    @BeforeEach
    public void setUp() {
        accountItemMapper = new AccountItemMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(accountItemMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(accountItemMapper.fromId(null)).isNull();
    }
}
