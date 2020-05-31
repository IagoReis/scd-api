package br.com.mundodev.scd.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.mundodev.scd.api.model.Acesso;
import br.com.mundodev.scd.api.model.CodigoAcessoId;

@Repository
public interface AcessoRepository extends JpaRepository<Acesso, CodigoAcessoId> {

	List<Acesso> findByIdIdConvenioAndIdIdTomadorAndDataTerminoNull(final Long idConvenio, final Long idTomador);
	
}
