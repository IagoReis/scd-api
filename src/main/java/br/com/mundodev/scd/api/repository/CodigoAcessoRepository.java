package br.com.mundodev.scd.api.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.mundodev.scd.api.enumeration.StatusCodigoAcesso;
import br.com.mundodev.scd.api.model.CodigoAcesso;
import br.com.mundodev.scd.api.model.CodigoAcessoId;

@Repository
public interface CodigoAcessoRepository extends JpaRepository<CodigoAcesso, CodigoAcessoId> {

	List<CodigoAcesso> findByIdIdConvenioAndIdIdTomador(final Long idConvenio, final Long idTomador);
	
	List<CodigoAcesso> findByIdIdConvenioAndIdIdTomadorAndStatusCodigoAcesso(final Long idConvenio, final Long idTomador, final StatusCodigoAcesso status);

	List<CodigoAcesso> findAllByStatusCodigoAcessoAndDataExpiracaoBefore(final StatusCodigoAcesso status, final LocalDateTime dataExpiracao);
	
}
