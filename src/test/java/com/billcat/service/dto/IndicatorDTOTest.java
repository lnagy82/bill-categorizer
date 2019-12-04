package com.billcat.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.billcat.web.rest.TestUtil;

public class IndicatorDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(IndicatorDTO.class);
        IndicatorDTO indicatorDTO1 = new IndicatorDTO();
        indicatorDTO1.setId(1L);
        IndicatorDTO indicatorDTO2 = new IndicatorDTO();
        assertThat(indicatorDTO1).isNotEqualTo(indicatorDTO2);
        indicatorDTO2.setId(indicatorDTO1.getId());
        assertThat(indicatorDTO1).isEqualTo(indicatorDTO2);
        indicatorDTO2.setId(2L);
        assertThat(indicatorDTO1).isNotEqualTo(indicatorDTO2);
        indicatorDTO1.setId(null);
        assertThat(indicatorDTO1).isNotEqualTo(indicatorDTO2);
    }
}
