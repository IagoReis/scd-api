package br.com.mundodev.scd.api.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import br.com.mundodev.scd.api.domain.Tomador;
import br.com.mundodev.scd.api.model.Acesso;
import br.com.mundodev.scd.api.model.CodigoAcesso;
import br.com.mundodev.scd.api.model.CodigoAcessoId;
import br.com.mundodev.scd.api.repository.AcessoRepository;
import br.com.mundodev.scd.api.service.AcessoService;

@Service
public class AcessoServiceImpl implements AcessoService {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	private AcessoRepository acessoRepository;
	
	public AcessoServiceImpl(final AcessoRepository acessoRepository) {
		this.acessoRepository = acessoRepository;
	}

	@Override
	public Acesso createAcesso(final Tomador tomador, final CodigoAcesso codigoAcesso) {
		return createAcesso(tomador, codigoAcesso, null);
	}
	
	@Override
	public Acesso createAcesso(final Tomador tomador, final CodigoAcesso codigoAcesso, final String ipAdress) {
		
		logger.info("Criando acesso para o tomador {}, código de acesso {} e ip {}", tomador, codigoAcesso);
		
		final var acessoId = new CodigoAcessoId();
		acessoId.setIdConvenio(tomador.getConvenio().getId());
		acessoId.setIdTomador(tomador.getId());
		acessoId.setCodigoAcesso(codigoAcesso.getId().getCodigoAcesso());
		
		final var acesso = Acesso.builder().id(acessoId).dataInicio(LocalDateTime.now()).ipInicio(ipAdress).build();
		
		final var save = acessoRepository.save(acesso);
		
		return save;		
	}

	@Override
	public void encerraAcesso(final CodigoAcesso codigoAcesso, final String ipAdress) {
		
		logger.info("Encerrando o acesso através do codigo de acesso {}", codigoAcesso);
		
		final var acessos = acessoRepository.findByIdIdConvenioAndIdIdTomadorAndDataTerminoNull(codigoAcesso.getId().getIdConvenio(), codigoAcesso.getId().getIdTomador());
		
		for (final Acesso acesso : acessos) {			
			acesso.setDataTermino(LocalDateTime.now());			
			acesso.setIpTermino(ipAdress);
			acessoRepository.save(acesso);
		}
	}

	@Override
	public Optional<Acesso> getAcessoNaoEncerrado(final Tomador tomador) {
		
		logger.info("Buscando acesso não encerrados através do tomador {}", tomador);
		
		final var acessos = acessoRepository.findByIdIdConvenioAndIdIdTomadorAndDataTerminoNull(tomador.getConvenio().getId(), tomador.getId());
		
		final Optional<Acesso> result = acessos.isEmpty() ? Optional.empty() : Optional.of(acessos.get(0));
		
		return result;
	}

}
