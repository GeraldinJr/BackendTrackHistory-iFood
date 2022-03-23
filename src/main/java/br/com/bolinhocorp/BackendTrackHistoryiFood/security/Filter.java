package br.com.bolinhocorp.BackendTrackHistoryiFood.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class Filter extends OncePerRequestFilter {
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
	// aqui vem a lógica de autorizar ou não
		// o que eu preciso fazer num filtro?
		// validar minha lógica de autrização
		if (request.getHeader("Authorization") != null) {
			Authentication auth = TokenUtil.validate(request);   // verifico se é válida
			// se for válida, insiro um cabeçalho do tipo Authentication indicando que a requisição é autenticada
			SecurityContextHolder.getContext().setAuthentication(auth);			
		}
		
		// encaminhar a requisição pra frente
		filterChain.doFilter(request, response);		
	}
}
