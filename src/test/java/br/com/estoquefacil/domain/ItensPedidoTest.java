package br.com.estoquefacil.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.estoquefacil.web.rest.TestUtil;

public class ItensPedidoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ItensPedido.class);
        ItensPedido itensPedido1 = new ItensPedido();
        itensPedido1.setId(1L);
        ItensPedido itensPedido2 = new ItensPedido();
        itensPedido2.setId(itensPedido1.getId());
        assertThat(itensPedido1).isEqualTo(itensPedido2);
        itensPedido2.setId(2L);
        assertThat(itensPedido1).isNotEqualTo(itensPedido2);
        itensPedido1.setId(null);
        assertThat(itensPedido1).isNotEqualTo(itensPedido2);
    }
}
