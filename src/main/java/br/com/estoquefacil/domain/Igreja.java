package br.com.estoquefacil.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Igreja.
 */
@Entity
@Table(name = "igreja")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Igreja implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nome_localidade", nullable = false)
    private String nomeLocalidade;

    @NotNull
    @Column(name = "numero_localidade", nullable = false)
    private String numeroLocalidade;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeLocalidade() {
        return nomeLocalidade;
    }

    public Igreja nomeLocalidade(String nomeLocalidade) {
        this.nomeLocalidade = nomeLocalidade;
        return this;
    }

    public void setNomeLocalidade(String nomeLocalidade) {
        this.nomeLocalidade = nomeLocalidade;
    }

    public String getNumeroLocalidade() {
        return numeroLocalidade;
    }

    public Igreja numeroLocalidade(String numeroLocalidade) {
        this.numeroLocalidade = numeroLocalidade;
        return this;
    }

    public void setNumeroLocalidade(String numeroLocalidade) {
        this.numeroLocalidade = numeroLocalidade;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Igreja)) {
            return false;
        }
        return id != null && id.equals(((Igreja) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Igreja{" +
            "id=" + getId() +
            ", nomeLocalidade='" + getNomeLocalidade() + "'" +
            ", numeroLocalidade='" + getNumeroLocalidade() + "'" +
            "}";
    }
}
