package br.com.estoquefacil.repository;
import br.com.estoquefacil.domain.ItensPedido;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ItensPedido entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ItensPedidoRepository extends JpaRepository<ItensPedido, Long> {

}
