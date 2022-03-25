package br.com.bolinhocorp.BackendTrackHistoryiFood.services;

import br.com.bolinhocorp.BackendTrackHistoryiFood.dto.PessoaLoginDTO;
import br.com.bolinhocorp.BackendTrackHistoryiFood.dto.TokenENomeDTO;
import br.com.bolinhocorp.BackendTrackHistoryiFood.models.PessoaEntregadora;

public interface IPessoaEntregadora {
	
	public TokenENomeDTO gerarTokenUsuarioLogado(PessoaLoginDTO dadosLogin);
	public PessoaEntregadora recuperarPorEmail(String email);
	public PessoaEntregadora CadastrarPessoaEntregadora(PessoaEntregadora pessoa);
	public PessoaEntregadora findById(Integer id);

}
