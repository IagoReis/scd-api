package br.com.mundodev.scd.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.mundodev.scd.api.model.CodigoAcesso;

@Repository
public interface CodigoAcessoRepository extends JpaRepository<CodigoAcesso, Long> {

	Optional<CodigoAcesso> findFirstByIdConvenioAndIdTomador(final Long idConvenio, final Long idTomador);
	
}
