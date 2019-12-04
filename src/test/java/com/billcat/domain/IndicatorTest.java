package com.billcat.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.billcat.web.rest.TestUtil;

public class IndicatorTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Indicator.class);
        Indicator indicator1 = new Indicator();
        indicator1.setId(1L);
        Indicator indicator2 = new Indicator();
        indicator2.setId(indicator1.getId());
        assertThat(indicator1).isEqualTo(indicator2);
        indicator2.setId(2L);
        assertThat(indicator1).isNotEqualTo(indicator2);
        indicator1.setId(null);
        assertThat(indicator1).isNotEqualTo(indicator2);
    }
}
