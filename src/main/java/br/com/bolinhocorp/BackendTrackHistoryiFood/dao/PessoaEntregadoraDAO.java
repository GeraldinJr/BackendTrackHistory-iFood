package br.com.bolinhocorp.BackendTrackHistoryiFood.dao;

import org.springframework.data.repository.CrudRepository;

import br.com.bolinhocorp.BackendTrackHistoryiFood.models.PessoaEntregadora;

public interface PessoaEntregadoraDAO extends CrudRepository<PessoaEntregadora, Integer>{
	public PessoaEntregadora findByEmail(String email);
}
