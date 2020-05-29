package br.com.mundodev.scd.api.scheduled;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.mundodev.scd.api.enumeration.StatusCodigoAcesso;
import br.com.mundodev.scd.api.repository.CodigoAcessoRepository;
import br.com.mundodev.scd.api.service.CodigoAcessoService;

@Component
public class CodigoAcessoScheduled {
	
	private CodigoAcessoRepository codigoAcessoRepository;	
	private CodigoAcessoService codigoAcessoService;
	
	@Autowired
	public CodigoAcessoScheduled(
			final CodigoAcessoRepository codigoAcessoRepository,			
			final CodigoAcessoService codigoAcessoService
		) {
		this.codigoAcessoRepository = codigoAcessoRepository;
		this.codigoAcessoService = codigoAcessoService;
	}

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Scheduled(cron = "0 */1 * * * *")
	public void updateStatusCodigoAcesso() {
		
		logger.info("Alterando o status dos códigos de acesso de {} para {}", StatusCodigoAcesso.PENDENTE, StatusCodigoAcesso.EXPIRADO);
		
		final var codigoAcessoPendenteList = codigoAcessoRepository.findAllByStatusAndDataExpiracaoBefore(StatusCodigoAcesso.PENDENTE, LocalDateTime.now());
		
		codigoAcessoPendenteList.forEach(codigoAcesso -> codigoAcessoService.updateStatus(codigoAcesso, StatusCodigoAcesso.EXPIRADO));
		
		logger.info("Alterando o status dos códigos de acesso de {} para {}", StatusCodigoAcesso.ATIVO, StatusCodigoAcesso.VALIDADO);
		
		final var codigoAcessoAtivoList = codigoAcessoRepository.findAllByStatusAndDataExpiracaoBefore(StatusCodigoAcesso.ATIVO, LocalDateTime.now());
		
		codigoAcessoAtivoList.forEach(codigoAcesso -> codigoAcessoService.updateStatus(codigoAcesso, StatusCodigoAcesso.VALIDADO));
	}
	
}
