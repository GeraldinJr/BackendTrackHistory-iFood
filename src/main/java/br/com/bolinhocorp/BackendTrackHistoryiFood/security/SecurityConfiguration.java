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
					  .antMatchers(HttpMethod.POST, "/pessoa-entregadora/login").permitAll()
					  .antMatchers(HttpMethod.POST, "/pessoa-entregadora/cadastro").permitAll()
					  .antMatchers(HttpMethod.GET, "/pedidos").permitAll()
					  .antMatchers(HttpMethod.GET, "/swagger-ui.html").permitAll()
					  .antMatchers(HttpMethod.GET, "/swagger-ui/index.html").permitAll()
					  .antMatchers(HttpMethod.GET, "/swagger-ui/swagger-ui.css").permitAll()
					  .antMatchers(HttpMethod.GET, "/swagger-ui/swagger-ui-standalone-preset.js").permitAll()
					  .antMatchers(HttpMethod.GET, "/swagger-ui/swagger-ui-bundle.js").permitAll()
					  .antMatchers(HttpMethod.GET, "/api-docs/swagger-config").permitAll()
					  .antMatchers(HttpMethod.GET, "/api-docs").permitAll()
					  .antMatchers(HttpMethod.GET, "/swagger-ui/favicon-32x32.png").permitAll()
					  
					  .anyRequest().authenticated().and().cors();
		
		// agora eu preciso indicar qual o filtro que eu quero que a requisição passe e como esse filtro trata a requisição
		httpSec.addFilterBefore(new Filter(), UsernamePasswordAuthenticationFilter.class);
		
	}
}
