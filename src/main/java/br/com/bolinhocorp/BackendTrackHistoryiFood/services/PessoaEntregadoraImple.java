package br.com.bolinhocorp.BackendTrackHistoryiFood.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import br.com.bolinhocorp.BackendTrackHistoryiFood.dao.PessoaEntregadoraDAO;
import br.com.bolinhocorp.BackendTrackHistoryiFood.dto.PessoaLoginDTO;
import br.com.bolinhocorp.BackendTrackHistoryiFood.dto.TokenENomeDTO;
import br.com.bolinhocorp.BackendTrackHistoryiFood.exceptions.DadosInvalidosException;
import br.com.bolinhocorp.BackendTrackHistoryiFood.exceptions.ErroInternoException;
import br.com.bolinhocorp.BackendTrackHistoryiFood.exceptions.NaoAutorizadoAuthException;
import br.com.bolinhocorp.BackendTrackHistoryiFood.models.PessoaEntregadora;
import br.com.bolinhocorp.BackendTrackHistoryiFood.security.Cripto;
import br.com.bolinhocorp.BackendTrackHistoryiFood.security.TokenUtil;

@Component
public class PessoaEntregadoraImple implements IPessoaEntregadora {

	@Autowired
	private PessoaEntregadoraDAO dao;

	@Override
	public TokenENomeDTO gerarTokenUsuarioLogado(PessoaLoginDTO dadosLogin) {
		try {
			PessoaEntregadora pessoa = dao.findByEmail(dadosLogin.getEmail());
			
			if (pessoa != null) {
				String senhaLogin = Cripto.encrypt(dadosLogin.getSenha());
				
				if(!pessoa.getSenha().equals(senhaLogin)) {
					throw new NaoAutorizadoAuthException("E-mail e/ou senha incorreto(s)");
				}
				
				return new TokenENomeDTO(TokenUtil.createToken(dadosLogin, pessoa.getId()), pessoa.getNome());
				
			}

			return null;
		} catch(NaoAutorizadoAuthException e) {
			throw new NaoAutorizadoAuthException(e.getMessage());
		}
		catch (Exception e) {
			throw new ErroInternoException(e.getMessage());
		}
	}

	@Override
	public PessoaEntregadora recuperarPorEmail(String email) {
		return dao.findByEmail(email);
	}

	@Override
	public PessoaEntregadora CadastrarPessoaEntregadora(PessoaEntregadora pessoa) {
		try {
			pessoa.setId(null);
			
			if(pessoa.getSenha().length()<6) {
				throw new DadosInvalidosException("A senha deve ter 6 ou mais caracteres");
			}
			pessoa.setSenha(Cripto.encrypt(pessoa.getSenha()));
			return dao.save(pessoa);
			
		} catch (DataIntegrityViolationException e) {
			throw new DadosInvalidosException("Dados invalidos");
		} catch (NullPointerException ex) {
			throw new DadosInvalidosException("Dados invalidos");
		} catch (Exception e) {
			throw new ErroInternoException(e.getMessage());
		}
	}

	@Override
	public PessoaEntregadora findById(Integer id) {
		return dao.findById(id).orElse(null);
	}

}
