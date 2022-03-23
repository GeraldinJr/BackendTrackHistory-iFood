package br.com.bolinhocorp.BackendTrackHistoryiFood.security;

import java.security.Key;
import java.util.Collections;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import br.com.bolinhocorp.BackendTrackHistoryiFood.dto.PessoaLoginDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class TokenUtil {
	private static final int    SEGUNDOS = 1000;
	private static final int    MINUTOS  = 60*SEGUNDOS;
	private static final int    HORAS    = 60*MINUTOS;
	private static final int    DIAS     = 24*HORAS;
	
	private static final String HEADER = "Authorization";  
	private static final String PREFIX = "Bearer ";        
	private static final long   EXPIRATION = 5*MINUTOS;    
	private static final String SECRET_KEY = "0B0l1nh0D3J4v43st4f4z3nd05uc3550!";  
	private static final String EMISSOR    = "BolinhoDeJava";
	
	public static String createToken(PessoaLoginDTO pessoaDTO) {
		
		Key secretKey = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
		
		String token = Jwts.builder().setSubject(pessoaDTO.getEmail())
								     .setIssuer(EMISSOR)
								     .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
								     .signWith(secretKey, SignatureAlgorithm.HS256)
								     .compact();
		
		return PREFIX + token;
	}
	
	private static boolean isExpirationValid(Date expiration) {
		return expiration.after(new Date(System.currentTimeMillis()));		
	}
	private static boolean isEmissorValid(String emissor) {
		return emissor.equals(EMISSOR);
	}
	private static boolean isSubjectValid(String email) {
		return email!=null && email.length() > 0;
	}
	
	public static Authentication validate(HttpServletRequest request) {
		
		String token = request.getHeader(HEADER);
		token = token.replace(PREFIX, ""); 
		
		Jws<Claims> jwsClaims = Jwts.parserBuilder().setSigningKey(SECRET_KEY.getBytes())
				                                    .build()
				                                    .parseClaimsJws(token);
		
		String email = jwsClaims.getBody().getSubject();
		String issuer   = jwsClaims.getBody().getIssuer();
		Date   expira   = jwsClaims.getBody().getExpiration();
		
		
		if (isSubjectValid(email) && isEmissorValid(issuer) && isExpirationValid(expira)) {
			
			return new UsernamePasswordAuthenticationToken(email, null, Collections.emptyList());
		}
	
		return null; 
	}
}