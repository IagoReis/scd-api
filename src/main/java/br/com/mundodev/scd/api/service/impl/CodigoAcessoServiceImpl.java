package br.com.mundodev.scd.api.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.mundodev.scd.api.domain.Tomador;
import br.com.mundodev.scd.api.enumeration.StatusCodigoAcesso;
import br.com.mundodev.scd.api.mail.Mailer;
import br.com.mundodev.scd.api.model.CodigoAcesso;
import br.com.mundodev.scd.api.repository.CodigoAcessoRepository;
import br.com.mundodev.scd.api.service.CodigoAcessoService;
import br.com.mundodev.scd.api.utils.AppUtils;

@Service
public class CodigoAcessoServiceImpl implements CodigoAcessoService {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Value("${app.codigo-acesso.maximo-horas-ativo}")
	private Integer maximoHorasAtivo;
	
	@Value("${app.codigo-acesso.maximo-horas-pendente}")
	private Integer maximoHorasPendente;

	private Mailer mailer;
	
	private CodigoAcessoRepository codigoAcessoRepository;

	public CodigoAcessoServiceImpl(final Mailer mailer, final CodigoAcessoRepository codigoAcessoRepository) {
		this.mailer = mailer;
		this.codigoAcessoRepository = codigoAcessoRepository;
	}
	
	@Override
	public CodigoAcesso createCodigoAcesso(final Tomador tomador) {
		
		logger.info("Criando código de acesso para o tomador {}", tomador);
		
		cancelaCodigoAcessoPendenteByFuncionario(tomador);
		validaCodigoAcessoAtivoByFuncionario(tomador);
		
		final String codigo = AppUtils.getRandomNumberString();
		
		final LocalDateTime dataGeracao = LocalDateTime.now();
		final LocalDateTime dataExpiracao = dataGeracao.plusHours(maximoHorasPendente);
		
		final var codigoAcesso = new CodigoAcesso();
		
		codigoAcesso.setCodigo(codigo);
		codigoAcesso.setIdConvenio(tomador.getConvenio().getId());
		codigoAcesso.setIdTomador(tomador.getId());
		codigoAcesso.setDataGeracao(dataGeracao);
		codigoAcesso.setDataExpiracao(dataExpiracao);
		codigoAcesso.setStatus(StatusCodigoAcesso.PENDENTE);
		
		final CodigoAcesso save = codigoAcessoRepository.save(codigoAcesso);
		
		mailer.enviar(tomador.getEmail(), "Código de acesso", String.format("Seu código de acesso é %s", save.getCodigo()));
		
		return save;
	}
	
	@Override
	public Optional<CodigoAcesso> getCodigoAcessoPendenteByTomador(final Tomador tomador) {
		
		logger.info("Buscando código de acesso PENDENTE do tomador {}", tomador);
		
		final List<CodigoAcesso> codigoAcessoList = codigoAcessoRepository.findByIdConvenioAndIdTomadorAndStatus(tomador.getConvenio().getId(), tomador.getId(), StatusCodigoAcesso.PENDENTE);
		
		final Optional<CodigoAcesso> result = codigoAcessoList.isEmpty() ? Optional.empty() : Optional.of(codigoAcessoList.get(0));
		
		return result;
	}
	
	@Override
	public Optional<CodigoAcesso> getCodigoAcessoAtivoByFuncionario(final Tomador tomador) {
		
		logger.info("Buscando código de acesso ATIVO do tomador {}", tomador);
		
		final List<CodigoAcesso> codigoAcessoList = codigoAcessoRepository.findByIdConvenioAndIdTomadorAndStatus(tomador.getConvenio().getId(), tomador.getId(), StatusCodigoAcesso.ATIVO);
		
		final Optional<CodigoAcesso> result = codigoAcessoList.isEmpty() ? Optional.empty() : Optional.of(codigoAcessoList.get(0));
		
		return result;
	}
	
	@Override
	public List<CodigoAcesso> getCodigoAcessoByTomadorAndStatus(final Tomador tomador, final StatusCodigoAcesso status) {
		
		logger.info("Buscando todos códigos de acesso do tomador {} que estejam com o status {}", tomador, status);
		
		final List<CodigoAcesso> codigoAcessoList = codigoAcessoRepository.findByIdConvenioAndIdTomadorAndStatus(tomador.getConvenio().getId(), tomador.getId(), status);
		
		return codigoAcessoList;
	}
	
	//TODO: Verificar se este método está sendo utilizado em mais algum lugar, se não estiver o excluir
	@Deprecated
	@Override
	public void updateStatusCodigoAcesso(final CodigoAcesso codigoAcesso, final StatusCodigoAcesso newStatus) {		
		updateStatus(codigoAcesso, newStatus);
	}

	@Override
	public void cancelaCodigoAcessoPendenteByFuncionario(final Tomador tomador) {
		
		logger.info("Cancelando código de acesso PENDENTE do tomador {}", tomador);
		
		final List<CodigoAcesso> codigoAcessoList = getCodigoAcessoByTomadorAndStatus(tomador, StatusCodigoAcesso.PENDENTE);
		
		for (final CodigoAcesso codigoAcesso : codigoAcessoList) {
			updateStatusCodigoAcesso(codigoAcesso, StatusCodigoAcesso.CANCELADO);
		}		
	}
	
	@Override
	public void validaCodigoAcessoAtivoByFuncionario(final Tomador tomador) {
		
		logger.info("Validando código de acesso ATIVO do tomador {}", tomador);
		
		final List<CodigoAcesso> codigoAcessoList = getCodigoAcessoByTomadorAndStatus(tomador, StatusCodigoAcesso.ATIVO);
		
		for (final CodigoAcesso codigoAcesso : codigoAcessoList) {
			updateStatusCodigoAcesso(codigoAcesso, StatusCodigoAcesso.VALIDADO);
		}		
	}

	@Override
	public void updateStatus(final CodigoAcesso codigoAcessoParam, final StatusCodigoAcesso status) {
		
		logger.info("Alterando status do código de acesso {} para {}", codigoAcessoParam, status);
		
		final var codigoAcessoOptional = codigoAcessoRepository.findById(codigoAcessoParam.getId());
		
		if (codigoAcessoOptional.isPresent()) {
			
			final var codigoAcesso = codigoAcessoOptional.get();
			
			codigoAcesso.setStatus(status);
			
			codigoAcessoRepository.save(codigoAcesso);
		}		
	}

	@Override
	public void ativaCodigoAcesso(final CodigoAcesso codigoAcessoParam) {
		
		logger.info("Ativando código de acesso {}", codigoAcessoParam);
		
		final var codigoAcessoOptional = codigoAcessoRepository.findById(codigoAcessoParam.getId());
		
		if (codigoAcessoOptional.isPresent()) {
			
			final var codigoAcesso = codigoAcessoOptional.get();
			
			codigoAcesso.setStatus(StatusCodigoAcesso.ATIVO);
			codigoAcesso.setDataExpiracao(LocalDateTime.now().plusHours(maximoHorasAtivo));
			
			codigoAcessoRepository.save(codigoAcesso);
		}
		
	}

	@Override
	public void encerraCodigoAcesso(final CodigoAcesso codigoAcessoParam) {
		
		logger.info("Encerrando código de acesso {}", codigoAcessoParam);
		
		final var codigoAcessoOptional = codigoAcessoRepository.findById(codigoAcessoParam.getId());
		
		if (codigoAcessoOptional.isPresent()) {
			
			final var codigoAcesso = codigoAcessoOptional.get();
			
			if (StatusCodigoAcesso.ATIVO.equals(codigoAcesso.getStatus())) {
				codigoAcesso.setStatus(StatusCodigoAcesso.VALIDADO);
			}
			else if (StatusCodigoAcesso.PENDENTE.equals(codigoAcesso.getStatus())) {
				codigoAcesso.setStatus(StatusCodigoAcesso.CANCELADO);
			}
			else {
				return;
			}
			
			codigoAcessoRepository.save(codigoAcesso);
		}
		
	}
}
