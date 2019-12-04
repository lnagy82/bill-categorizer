package com.billcat.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.billcat.web.rest.TestUtil;

public class AccountItemTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AccountItem.class);
        AccountItem accountItem1 = new AccountItem();
        accountItem1.setId(1L);
        AccountItem accountItem2 = new AccountItem();
        accountItem2.setId(accountItem1.getId());
        assertThat(accountItem1).isEqualTo(accountItem2);
        accountItem2.setId(2L);
        assertThat(accountItem1).isNotEqualTo(accountItem2);
        accountItem1.setId(null);
        assertThat(accountItem1).isNotEqualTo(accountItem2);
    }
}
