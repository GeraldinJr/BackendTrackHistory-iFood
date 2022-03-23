package br.com.bolinhocorp.BackendTrackHistoryiFood.services;

import br.com.bolinhocorp.BackendTrackHistoryiFood.dto.PessoaLoginDTO;
import br.com.bolinhocorp.BackendTrackHistoryiFood.models.PessoaEntregadora;
import br.com.bolinhocorp.BackendTrackHistoryiFood.security.Token;

public interface IPessoaEntregadora {
	
	public Token gerarTokenUsuarioLogado(PessoaLoginDTO dadosLogin);
	public PessoaEntregadora recuperarPorEmail(String email);
	public PessoaEntregadora CadastrarPessoaEntregadora(PessoaEntregadora pessoa);

}
