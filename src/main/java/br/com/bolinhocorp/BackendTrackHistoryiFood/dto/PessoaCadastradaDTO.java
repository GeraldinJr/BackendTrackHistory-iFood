package br.com.bolinhocorp.BackendTrackHistoryiFood.dto;

import br.com.bolinhocorp.BackendTrackHistoryiFood.models.PessoaEntregadora;

public class PessoaCadastradaDTO {
	private String email;
	private String nome;
	
	public  PessoaCadastradaDTO(PessoaEntregadora pessoa) {
		super();
		this.email = pessoa.getEmail();
		this.nome = pessoa.getNome();
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	

}
