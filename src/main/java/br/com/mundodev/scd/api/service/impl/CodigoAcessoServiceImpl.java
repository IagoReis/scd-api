package br.com.mundodev.scd.api.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.mundodev.scd.api.domain.FuncionarioApi;
import br.com.mundodev.scd.api.enumeration.StatusCodigoAcesso;
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
		
		cancelaCodigoAcessoPendenteByFuncionario(funcionario);
		validaCodigoAcessoAtivoByFuncionario(funcionario);
		
		final String codigo = AppUtils.getRandomNumberString();
		
		final LocalDateTime dataGeracao = LocalDateTime.now();
		final LocalDateTime dataExpiracao = dataGeracao.plusHours(3);
		
		final var codigoAcesso = new CodigoAcesso();
		
		codigoAcesso.setCodigo(codigo);
		codigoAcesso.setIdTomador(funcionario.getId());
		codigoAcesso.setDataGeracao(dataGeracao);
		codigoAcesso.setDataExpiracao(dataExpiracao);
		codigoAcesso.setStatus(StatusCodigoAcesso.PENDENTE);
		
		final CodigoAcesso save = codigoAcessoRepository.save(codigoAcesso);
		
		mailer.enviar(funcionario.getEmail(), "Código de acesso", String.format("Seu código de acesso é %s", save.getCodigo()));
		
		return save;
	}
	
	@Override
	public Optional<CodigoAcesso> getCodigoAcessoPendenteByFuncionario(final FuncionarioApi funcionario) {
		
		final List<CodigoAcesso> codigoAcessoList = codigoAcessoRepository.findByIdTomadorAndStatus(funcionario.getId(), StatusCodigoAcesso.PENDENTE);
		
		final Optional<CodigoAcesso> result = codigoAcessoList.isEmpty() ? Optional.empty() : Optional.of(codigoAcessoList.get(0));
		
		return result;
	}
	
	@Override
	public Optional<CodigoAcesso> getCodigoAcessoAtivoByFuncionario(final FuncionarioApi funcionario) {
		
		final List<CodigoAcesso> codigoAcessoList = codigoAcessoRepository.findByIdTomadorAndStatus(funcionario.getId(), StatusCodigoAcesso.ATIVO);
		
		final Optional<CodigoAcesso> result = codigoAcessoList.isEmpty() ? Optional.empty() : Optional.of(codigoAcessoList.get(0));
		
		return result;
	}
	
	@Override
	public List<CodigoAcesso> getCodigoAcessoByFuncionarioAndStatus(final FuncionarioApi funcionario, final StatusCodigoAcesso status) {
		
		final List<CodigoAcesso> codigoAcessoList = codigoAcessoRepository.findByIdTomadorAndStatus(funcionario.getId(), status);
		
		return codigoAcessoList;
	}
	
	@Override
	public void updateStatusCodigoAcesso(final CodigoAcesso codigoAcesso, final StatusCodigoAcesso newStatus) {
		
		codigoAcesso.setStatus(newStatus);
		
		codigoAcessoRepository.save(codigoAcesso);
	}

	@Override
	public void cancelaCodigoAcessoPendenteByFuncionario(final FuncionarioApi funcionario) {
		
		final List<CodigoAcesso> codigoAcessoList = getCodigoAcessoByFuncionarioAndStatus(funcionario, StatusCodigoAcesso.PENDENTE);
		
		for (final CodigoAcesso codigoAcesso : codigoAcessoList) {
			updateStatusCodigoAcesso(codigoAcesso, StatusCodigoAcesso.CANCELADO);
		}		
	}
	
	@Override
	public void validaCodigoAcessoAtivoByFuncionario(final FuncionarioApi funcionario) {
		
		final List<CodigoAcesso> codigoAcessoList = getCodigoAcessoByFuncionarioAndStatus(funcionario, StatusCodigoAcesso.ATIVO);
		
		for (final CodigoAcesso codigoAcesso : codigoAcessoList) {
			updateStatusCodigoAcesso(codigoAcesso, StatusCodigoAcesso.VALIDADO);
		}		
	}
}
