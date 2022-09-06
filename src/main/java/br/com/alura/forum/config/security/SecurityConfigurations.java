package br.com.alura.forum.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity // habilita o modulo de segiranca
@Configurable // para o spring ler as configurações que estap dentro desta classe
public class SecurityConfigurations extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private AutenticacaoService autenticacaoService;
	
@Override
@Bean
protected AuthenticationManager authenticationManager() throws Exception {
	return super.authenticationManager();
}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// serve para configurar a parte de autenticação. Ex: controle de acesso,
		// login...
		auth.userDetailsService(autenticacaoService)// qual a classe que tem a logica de autenticação? AutenticacaoService
		.passwordEncoder(new BCryptPasswordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// configuração de autrização, url. Ex: quem pode acessar cada url, perfil de
		// acesso...
		
		http.authorizeRequests()
		.antMatchers(HttpMethod.GET,  "/topicos").permitAll() // o metodo antMatchers diz quais urls serao permitidas e quais serão bloqueadas
		.antMatchers(HttpMethod.GET,  "/topicos/*").permitAll()
		.antMatchers(HttpMethod.POST, "/auth").permitAll()
		/* o acesso esta liberado para chamadas tipo get com as requisições 
		 * "/topicos" e "/topicos/algumId"
		 */
		.anyRequest().authenticated() // todos os outros metodos tem que estarem autenticado para serema acessados,
		//as URLs que não foram configuradas devem ter acesso restrito
		
//		.and().formLogin();// diz ao spring para gerar um formulario de identifição"tela de login"
	.and().csrf().disable() // desabilitar a proteção de ataque haker via csrf
	.sessionManagement() // para dizer que nao queremos usar a ciração de seção
	.sessionCreationPolicy(SessionCreationPolicy.STATELESS); 
//		Com isso, aviso para o Spring security que no nosso projeto,
//		quando eu fizer autenticação, não é para criar sessão, porque vamos usar token
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		// configuração de recursos staticos, requisição para arquivos cc, imagens,
		// javascript

	}
//	public static void main(String[] args) {
//		// só para gera  ohash da senha 123456
//		System.out.println(new BCryptPasswordEncoder().encode("123456") );
//	}
}
