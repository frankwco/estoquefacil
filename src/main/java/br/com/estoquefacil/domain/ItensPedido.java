package br.com.estoquefacil.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A ItensPedido.
 */
@Entity
@Table(name = "itens_pedido")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ItensPedido implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "quantidade_produto", nullable = false)
    private Double quantidadeProduto;

    @NotNull
    @Column(name = "valor_produto", nullable = false)
    private Double valorProduto;

    @ManyToOne
    private Pedido pedido;

    @ManyToOne
    private Produto produto;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getQuantidadeProduto() {
        return quantidadeProduto;
    }

    public ItensPedido quantidadeProduto(Double quantidadeProduto) {
        this.quantidadeProduto = quantidadeProduto;
        return this;
    }

    public void setQuantidadeProduto(Double quantidadeProduto) {
        this.quantidadeProduto = quantidadeProduto;
    }

    public Double getValorProduto() {
        return valorProduto;
    }

    public ItensPedido valorProduto(Double valorProduto) {
        this.valorProduto = valorProduto;
        return this;
    }

    public void setValorProduto(Double valorProduto) {
        this.valorProduto = valorProduto;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public ItensPedido pedido(Pedido pedido) {
        this.pedido = pedido;
        return this;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Produto getProduto() {
        return produto;
    }

    public ItensPedido produto(Produto produto) {
        this.produto = produto;
        return this;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ItensPedido)) {
            return false;
        }
        return id != null && id.equals(((ItensPedido) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ItensPedido{" +
            "id=" + getId() +
            ", quantidadeProduto=" + getQuantidadeProduto() +
            ", valorProduto=" + getValorProduto() +
            "}";
    }
}
