package br.com.mundodev.scd.api.scheduled;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.mundodev.scd.api.enumeration.StatusCodigoAcesso;
import br.com.mundodev.scd.api.repository.CodigoAcessoRepository;
import br.com.mundodev.scd.api.service.AcessoService;
import br.com.mundodev.scd.api.service.CodigoAcessoService;

@Component
public class CodigoAcessoScheduled {
	
	private CodigoAcessoRepository codigoAcessoRepository;	
	private CodigoAcessoService codigoAcessoService;
	private AcessoService acessoService;
	
	@Autowired
	public CodigoAcessoScheduled(
			final CodigoAcessoRepository codigoAcessoRepository,			
			final CodigoAcessoService codigoAcessoService,
			final AcessoService acessoService
		) {
		this.codigoAcessoRepository = codigoAcessoRepository;
		this.codigoAcessoService = codigoAcessoService;
		this.acessoService = acessoService;
	}

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Scheduled(cron = "0 */5 * * * *")
	public void encerraCodigoAcessoPendente() {
		
		logger.info("JOB encerraCodigoAcessoPendente");
		logger.info("Encerrando os códigos de acesso de com status {} que estejam expirados", StatusCodigoAcesso.PENDENTE);
		
		final var codigosAcesso = codigoAcessoRepository.findAllByStatusCodigoAcessoAndDataExpiracaoBefore(StatusCodigoAcesso.PENDENTE, LocalDateTime.now());
		
		codigosAcesso.forEach(codigoAcesso -> {
			codigoAcessoService.encerraCodigoAcesso(codigoAcesso);
			acessoService.encerraAcesso(codigoAcesso, null);
		});
	}
	
	@Scheduled(cron = "0 */5 * * * *")
	public void encerraCodigoAcessoAtivo() {
		
		logger.info("JOB encerraCodigoAcessoPendente");
		logger.info("Encerrando os códigos de acesso de com status {} que estejam expirados", StatusCodigoAcesso.ATIVO);
		
		final var codigosAcesso = codigoAcessoRepository.findAllByStatusCodigoAcessoAndDataExpiracaoBefore(StatusCodigoAcesso.ATIVO, LocalDateTime.now());
		
		codigosAcesso.forEach(codigoAcesso -> {
			codigoAcessoService.encerraCodigoAcesso(codigoAcesso);
			acessoService.encerraAcesso(codigoAcesso, null);
		});
	}
	
}
