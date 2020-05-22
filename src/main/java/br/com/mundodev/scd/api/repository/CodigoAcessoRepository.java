package br.com.mundodev.scd.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.mundodev.scd.api.model.CodigoAcesso;

@Repository
public interface CodigoAcessoRepository extends JpaRepository<CodigoAcesso, Long> {

}
