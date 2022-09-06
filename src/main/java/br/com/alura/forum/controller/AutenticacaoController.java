package br.com.alura.forum.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.alura.forum.config.security.TokenService;
import br.com.alura.forum.controller.dto.TokenDto;
import br.com.alura.forum.controller.form.LoginForm;

@RestController
@RequestMapping("/auth")
public class AutenticacaoController {

	@Autowired
	private AuthenticationManager authManeger;//
//	Na classe SecurityConfigurations, devemos sobrescrever o metodo:
//	protected AuthenticationManager authenticationManager()
	@Autowired
	private TokenService tokenService;

	@PostMapping
	public ResponseEntity<TokenDto> autenticar(@RequestBody @Valid LoginForm form) {
//	 esse metodo vai ser chamado quando solicitar do usuario os dados de login e senha,

		UsernamePasswordAuthenticationToken dadosLogin = form.converter();
		// crira o metodo converter na classe LoginForme de maneira muito massa...xD

		// Tenta fazer a autenticação:
		Authentication authentication = authManeger.authenticate(dadosLogin);

		// pegar o email e a senha, fazer a autenticação no bando de dados e se tiver ok
		// mandamos gerar o token
//					criar a classe TokenService
		
		String token = tokenService.gerarToken(authentication);
		// dizer para o cliente uqal o tipo da autentitação que ele tera que fazer nas proximas especificações
		return ResponseEntity.ok(new TokenDto(token, "Bearer"));
		// com isso eu tenho como resposta o token e o tipo da autenticação para mandar ao cliente
		
//		System.out.println(token);
//		return ResponseEntity.ok().build();
		// se der certo, return ok!

	}
//	@PostMapping
//	public ResponseEntity<?> autenticar(@RequestBody @Valid LoginForm form) {
////	 esse metodo vai ser chamado quando solicitar do usuario os dados de login e senha,
//
//		UsernamePasswordAuthenticationToken dadosLogin = form.converter();
//		// crira o metodo converter na classe LoginForme de maneira muito massa...xD
//
//		try {
//// 			Tenta fazer a autenticação:
//			Authentication authentication = authManeger.authenticate(dadosLogin);
////			 pegar o email e a senha, fazer a autenticação no bando de dados e se tiver ok
////			 mandamos gerar o token
//			return ResponseEntity.ok().build();
//			// se der certo, return ok!
//
//		} catch (AuthenticationException e) {
//			// se der errado, vai ser lancado essa exception ai em cima, e quero que seja
//			// lancado o dabrequest como codigo 400- eu acho, nao lembro o que o professor
//			// falou...
//
//			return ResponseEntity.badRequest().build();
//
//		}
//
//	}
}
