package br.com.alura.forum.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.alura.forum.controller.dto.TopicoDto;
import br.com.alura.forum.controller.dto.TopicoForm;
import br.com.alura.forum.modelo.Topico;
import br.com.alura.forum.repository.CursoRepository;
import br.com.alura.forum.repository.TopicoRepository;

@RestController
@RequestMapping("/topicos")
public class TopicosController {
	
	@Autowired
	private TopicoRepository topicoRepository;
	
	@Autowired
	private CursoRepository cursoRepository;
	
	@GetMapping
	public List<TopicoDto> lista(String nomeCurso) {
		if(nomeCurso == null) {
			List<Topico> topicos= topicoRepository.findAll();
			return TopicoDto.converter(topicos);
//			http://localhost:8080/topicos
		}
		List<Topico> topicos= topicoRepository.findByCurso_Nome(nomeCurso);
		return TopicoDto.converter(topicos);
//		http://localhost:8080/topicos?nomeCurso=HTML+5
//		http://localhost:8080/topicos?nomeCurso=Spring+Boot
	}
	
//	@RequestMapping(value= "/topicos", method = RequestMethod.POST)
	@PostMapping
	public ResponseEntity<TopicoDto> cadatrar(@RequestBody @Valid TopicoForm form, UriComponentsBuilder uriBuilder) {
//		quando o springo for injeta a classe form, que ela execute a snotificações referente a cada atributo anotado
		
     // para avisar que os parametros vao estar no corpo da requisição, e nao como parametro d url usamos o @RequestBody
	//ResponseEntity vai resonder o codigo referente ao estado da transação, 200 ok, 201 ok e algo novo foi gerado e etc...
		Topico topico= form.converter(cursoRepository);
		topicoRepository.save(topico);
		
		URI uri= uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
		/*tenho que devolver um cabeçalho http chamado location com  url do recurso que acabou de ser criado
		 *  e no corpo da resposta uma representação d objeto que acabou de ser criada
		 */
		return ResponseEntity.created(uri).body(new TopicoDto(topico));
	}
	

}
