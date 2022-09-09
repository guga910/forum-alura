package br.com.alura.forum.config.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import br.com.alura.forum.modelo.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService {

	@Value("${forum.jwt.expiration}")
	private String expiration;

	@Value("${forum.jwt.secret}")
	private String secret;

	public String gerarToken(Authentication authentication) {

		Usuario logado = (Usuario) authentication.getPrincipal(); // vai me mostrar o usuario que esta logado
		Date hoje = new Date();
		Date exp = new Date(hoje.getTime() + Long.parseLong(expiration));
		return Jwts.builder().setIssuer("API do Fórum Alura").setSubject(logado.getId().toString()) // pegando o id do
																									// usuario logado e
																									// devolvendo uma
																									// string
				.setIssuedAt(hoje).setExpiration(exp)// esta no properties- forum.jwt.expiration=86400000- temmpo
														// referente a 1 dia
				.signWith(SignatureAlgorithm.HS256, secret)
				// criptografar o token= quem é o algoritimo a ser criptografado equal é o
				// "segredo"
				// secret é uma string aleatoria que esta no properties para gerar a seguraca do
				// token
				.compact(); // para compactar e tranformar isso em uma string
	}

	public Boolean isTokenValido(String token) {
		
		try {// ele havalia seo token esta valido, se im, return true, se nao, false
			// o try catch é que se o token nao estiver valido, uma exception sera lancada
			Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);	
			
			return true;
		} catch (Exception e) {
			
			return false;
		}
		
	}

	public Long getIdUsuario(String token) {
		// eu dou o token, pelo id ele consegue chegar ate o id do usuario

		Claims claims = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
		return Long.parseLong(claims.getSubject());
	}

}
