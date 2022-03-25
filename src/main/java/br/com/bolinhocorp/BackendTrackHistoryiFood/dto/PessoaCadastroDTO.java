package br.com.bolinhocorp.BackendTrackHistoryiFood.dto;

public class PessoaCadastroDTO {
	
	private String email;
	private String nome;
	private String senha;
	private String confirmacao_senha;
	
	
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
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getConfirmacao_senha() {
		return confirmacao_senha;
	}
	public void setConfirmacao_senha(String confirma_senha) {
		this.confirmacao_senha = confirma_senha;
	}
	
}
