package br.com.alura.forum.config.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.alura.forum.modelo.Usuario;
import br.com.alura.forum.repository.UsuarioRepository;

public class AutenticacaoViaTokenFilter extends OncePerRequestFilter {

	private TokenService tokenService= new TokenService();
	private UsuarioRepository usuarioRepository;
	
	public AutenticacaoViaTokenFilter(TokenService tokenService, UsuarioRepository usuarioRepository) {
		this.tokenService = tokenService;
		this.usuarioRepository= usuarioRepository;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
//		 pegar o token do cabeçalho, verificar se está ok, autenticar no Spring
		
		String token= recuperarToken(request);
//		System.out.println("meu token é: "+token);
		Boolean valido= tokenService.isTokenValido(token);
//		System.out.println("esta valido? "+valido);
		if(valido) {
			autenticarCliente(token);
		}
		
		// se o token estiver correto e validado, pode chamar o filterChain e autorizar a requisição!
		filterChain.doFilter(request, response);
//		falar para o Spring que já rodamos o que tínhamos que rodar nesse filtro.

	}

	private void autenticarCliente(String token) {
		Long idUsuario= tokenService.getIdUsuario(token); // peguei o id do token
		Usuario usuario= usuarioRepository.findById(idUsuario).get(); //recuperei o cusario passando o id
		UsernamePasswordAuthenticationToken Authentication=new //criei o UsernamePasswordAuthenticationToken passando o usuaio a senha nula e passando o perfil
				UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities()); // getAuthorities me devolve os perfis de acesso, que consta nosso usuario.
		SecurityContextHolder.getContext().setAuthentication(Authentication);// chamei a classe do spring que força a autenticação que é só para esse request. cada requisição tem que reautenticar o token, nossa requisiçaõ e stateles
		
		
	}

	private String recuperarToken(HttpServletRequest request) {
	String	token= request.getHeader("Authorization");// Authorization é o cabecalho que se encontra no headere da requisição... ra realidade, achei ele no postman...
	if(token== null || token.isEmpty() || token.startsWith("Baerer ")) {//token.startsWith se comeca com ...
		return null;
	}
	
	return token.substring(7, token.length()); // o token é composto da palavra barer e um espaco, depois vem a sequencia de caracters... desse modo, eu comeco a considerar meu tokem apos o setimo caracter, pulando assim a palavra e o espaço, comecando da sequencia de caracters
	}

}
