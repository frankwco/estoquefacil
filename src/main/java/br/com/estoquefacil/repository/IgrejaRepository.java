package br.com.estoquefacil.repository;
import br.com.estoquefacil.domain.Igreja;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Igreja entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IgrejaRepository extends JpaRepository<Igreja, Long> {

}
