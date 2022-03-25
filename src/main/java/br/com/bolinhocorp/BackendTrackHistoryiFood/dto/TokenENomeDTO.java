package br.com.bolinhocorp.BackendTrackHistoryiFood.dto;


public class TokenENomeDTO {
	private String token;
	private String nome;
	
	public TokenENomeDTO() {
		super();
	}
	public TokenENomeDTO(String token, String nome) {
		super();
		this.token = token;
		this.nome = nome;
	}
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	

}
