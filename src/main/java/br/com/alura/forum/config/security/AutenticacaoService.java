package br.com.alura.forum.config.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.alura.forum.modelo.Usuario;
import br.com.alura.forum.repository.UsuarioRepository;

@Service
public class AutenticacaoService implements UserDetailsService{
//	Para indicar ao Spring Security que essa é a classe service que executa a lógica de autenticação
	@Autowired
	private UsuarioRepository repository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		/*a tela de login manda o email para cá (username), devemos fazer a consulta no bando de dados
		 * prcurando pelo email, a senha é feta em memoria, buscamos no bando pelo email
		  e o spring faz a checagem da senha em memoria.*/
		 Optional<Usuario> usuario= repository.findByEmail(username);
		 if(usuario.isPresent()) {
			 return usuario.get();
		 }
		 
		throw new UsernameNotFoundException("Dados invalidos");
		/*Eu recebo o email pela tela do login, vejo se ele esta presente no meu bando de dados
		 * se tiver, eu retorno o usuario, se nao tiver, eu lanço a exception.
		 * 
		 */
	}

}
