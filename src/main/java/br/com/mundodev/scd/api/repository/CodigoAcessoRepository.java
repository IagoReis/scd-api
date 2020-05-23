package br.com.mundodev.scd.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.mundodev.scd.api.enumeration.StatusCodigoAcesso;
import br.com.mundodev.scd.api.model.CodigoAcesso;

@Repository
public interface CodigoAcessoRepository extends JpaRepository<CodigoAcesso, Long> {

	List<CodigoAcesso> findByIdTomadorAndStatus(final Long idTomador, final StatusCodigoAcesso status);
	
}
