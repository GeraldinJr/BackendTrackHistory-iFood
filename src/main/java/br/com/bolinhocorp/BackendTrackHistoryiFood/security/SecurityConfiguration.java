package br.com.bolinhocorp.BackendTrackHistoryiFood.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration 			// indico que é uma classe de configuração (e não um arquivo tipo o properties)
@EnableWebSecurity  
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	@Autowired
	private EntryPoint entryPoint;
	
	public void configure(HttpSecurity httpSec) throws Exception{
		httpSec.csrf().disable()
					  .exceptionHandling().authenticationEntryPoint(entryPoint)   // estou colocando um tratador de exceções
					  .and()
					  .authorizeRequests() 
					  // quais são as requisições que eu quero permitir
					  .antMatchers(HttpMethod.POST, "/login").permitAll()
//					  .antMatchers(HttpMethod.GET, "/produtos/*").permitAll()
//					  .antMatchers(HttpMethod.POST, "/login").permitAll()
//					  .antMatchers(HttpMethod.POST, "/usuarios*").permitAll()
//					  .antMatchers(HttpMethod.PUT, "/usuarios/*").permitAll()
					  
					  // qualquer outra requisição que "foge" aos padrões especificados, precisa ser autenticada
					  .anyRequest().authenticated().and().cors();
		
		// agora eu preciso indicar qual o filtro que eu quero que a requisição passe e como esse filtro trata a requisição
		httpSec.addFilterBefore(new Filter(), UsernamePasswordAuthenticationFilter.class);
		
	}
}
