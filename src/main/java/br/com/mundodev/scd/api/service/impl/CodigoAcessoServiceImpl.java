package br.com.mundodev.scd.api.service.impl;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import br.com.mundodev.scd.api.domain.FuncionarioApi;
import br.com.mundodev.scd.api.mail.Mailer;
import br.com.mundodev.scd.api.model.CodigoAcesso;
import br.com.mundodev.scd.api.repository.CodigoAcessoRepository;
import br.com.mundodev.scd.api.service.CodigoAcessoService;
import br.com.mundodev.scd.api.utils.AppUtils;

@Service
public class CodigoAcessoServiceImpl implements CodigoAcessoService {

	private Mailer mailer;
	
	private CodigoAcessoRepository codigoAcessoRepository;

	public CodigoAcessoServiceImpl(final Mailer mailer, final CodigoAcessoRepository codigoAcessoRepository) {
		this.mailer = mailer;
		this.codigoAcessoRepository = codigoAcessoRepository;
	}
	
	@Override
	public CodigoAcesso createCodigoAcesso(final FuncionarioApi funcionario) {
		
		final String codigo = AppUtils.getRandomNumberString();
		
		final LocalDateTime dataGeracao = LocalDateTime.now();
		final LocalDateTime dataExpiracao = dataGeracao.plusHours(3);
		
		final var codigoAcesso = new CodigoAcesso();
		
		codigoAcesso.setCodigo(codigo);
		codigoAcesso.setIdConvenio(1L);
		codigoAcesso.setIdTomador(Long.valueOf(funcionario.getId()));
		codigoAcesso.setDataGeracao(dataGeracao);
		codigoAcesso.setDataExpiracao(dataExpiracao);
		codigoAcesso.setTipoSituacao("A");
		
		final CodigoAcesso save = codigoAcessoRepository.save(codigoAcesso);
		
		mailer.enviar(funcionario.getEmail(), "Código de acesso", String.format("Seu código de acesso é %s", save.getCodigo()));
		
		return save;
	}

}
