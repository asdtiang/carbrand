package org.asdtiang.carbrand.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.asdtiang.carbrand.web.rest.TestUtil;

public class BrandTypeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BrandType.class);
        BrandType brandType1 = new BrandType();
        brandType1.setId(1L);
        BrandType brandType2 = new BrandType();
        brandType2.setId(brandType1.getId());
        assertThat(brandType1).isEqualTo(brandType2);
        brandType2.setId(2L);
        assertThat(brandType1).isNotEqualTo(brandType2);
        brandType1.setId(null);
        assertThat(brandType1).isNotEqualTo(brandType2);
    }
}
