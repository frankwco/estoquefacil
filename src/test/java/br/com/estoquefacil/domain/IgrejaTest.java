package br.com.estoquefacil.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.estoquefacil.web.rest.TestUtil;

public class IgrejaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Igreja.class);
        Igreja igreja1 = new Igreja();
        igreja1.setId(1L);
        Igreja igreja2 = new Igreja();
        igreja2.setId(igreja1.getId());
        assertThat(igreja1).isEqualTo(igreja2);
        igreja2.setId(2L);
        assertThat(igreja1).isNotEqualTo(igreja2);
        igreja1.setId(null);
        assertThat(igreja1).isNotEqualTo(igreja2);
    }
}
