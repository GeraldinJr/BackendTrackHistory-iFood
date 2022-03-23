package br.com.bolinhocorp.BackendTrackHistoryiFood.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import br.com.bolinhocorp.BackendTrackHistoryiFood.dao.PessoaEntregadoraDAO;
import br.com.bolinhocorp.BackendTrackHistoryiFood.dto.PessoaLoginDTO;
import br.com.bolinhocorp.BackendTrackHistoryiFood.exceptions.DadosInvalidosException;
import br.com.bolinhocorp.BackendTrackHistoryiFood.models.PessoaEntregadora;
import br.com.bolinhocorp.BackendTrackHistoryiFood.security.Token;
import br.com.bolinhocorp.BackendTrackHistoryiFood.security.TokenUtil;

@Component
public class PessoaEntregadoraImple implements IPessoaEntregadora {

	@Autowired
	private PessoaEntregadoraDAO dao;

	@Override
	public Token gerarTokenUsuarioLogado(PessoaLoginDTO dadosLogin) {
		try {
			PessoaEntregadora pessoa = dao.findByEmail(dadosLogin.getEmail());
			if (pessoa != null) {
				// Fazer o encript da senha e conferir as senhas
				return new Token(TokenUtil.createToken(dadosLogin));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public PessoaEntregadora recuperarPorEmail(String email) {
		return dao.findByEmail(email);
	}

	@Override
	public PessoaEntregadora CadastrarPessoaEntregadora(PessoaEntregadora pessoa) {
		try {
			pessoa.setId(null);
			return dao.save(pessoa);
			
		} catch (DataIntegrityViolationException e) {
			throw new DadosInvalidosException("Dados invalidos");
		} catch (NullPointerException ex) {
			throw new DadosInvalidosException("Dados invalidos");
		} catch (Exception e) {
			throw new DadosInvalidosException("Dados invalidos");
		}
	}

}
