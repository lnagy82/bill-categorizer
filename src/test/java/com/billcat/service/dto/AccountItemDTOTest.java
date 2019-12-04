package com.billcat.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.billcat.web.rest.TestUtil;

public class AccountItemDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AccountItemDTO.class);
        AccountItemDTO accountItemDTO1 = new AccountItemDTO();
        accountItemDTO1.setId(1L);
        AccountItemDTO accountItemDTO2 = new AccountItemDTO();
        assertThat(accountItemDTO1).isNotEqualTo(accountItemDTO2);
        accountItemDTO2.setId(accountItemDTO1.getId());
        assertThat(accountItemDTO1).isEqualTo(accountItemDTO2);
        accountItemDTO2.setId(2L);
        assertThat(accountItemDTO1).isNotEqualTo(accountItemDTO2);
        accountItemDTO1.setId(null);
        assertThat(accountItemDTO1).isNotEqualTo(accountItemDTO2);
    }
}
