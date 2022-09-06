package br.com.alura.forum.controller;

import java.net.URI;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.alura.forum.controller.dto.DetalhesTopicoDto;
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
	@Cacheable(value = "listaDeTopicos") //serve para indicar ao Spring o nome do cache associado a um determinado método
	public Page<TopicoDto> lista(@RequestParam(required = false) String nomeCurso,
			@PageableDefault(sort="id", direction= Direction.ASC, size=10)	Pageable paginacao) {

		//	public Page<TopicoDto> lista(@RequestParam(required = false) String nomeCurso, 
//			@RequestParam int pag,@RequestParam int qnt, @RequestParam String ordenacao) {

		//		http://localhost:8080/topicos?page=0&size=3&sort=id,desc // observar anotação @EnableSpringDataWebSupport na classe aplication
//		http://localhost:8080/topicos?pag=0&qnt=5
//		http://localhost:8080/topicos?pag=0&qnt=5&ordenacao=dataCriacao

		//@RequestParam diz que os parametros sao obrigatorios a serem pasados na url
		//required = diz se o parametro é obgatorio ou nao
		
//		Pageable pagicacao= PageRequest.of(pag, qnt);
//		Pageable pagicacao= PageRequest.of(pag, qnt, Direction.ASC, ordenacao);
		
		if(nomeCurso == null) {
//			List<Topico> topicos= topicoRepository.findAll();
			Page<Topico> topicos = topicoRepository.findAll(paginacao);
			return TopicoDto.converter(topicos);
//			http://localhost:8080/topicos
		}
//		List<Topico> topicos= topicoRepository.findByCurso_Nome(nomeCurso);
		Page<Topico> topicos= topicoRepository.findByCurso_Nome(nomeCurso, paginacao);
		return TopicoDto.converter(topicos);
//		http://localhost:8080/topicos?nomeCurso=HTML+5
//		http://localhost:8080/topicos?nomeCurso=Spring+Boot
	}
	
	@PostMapping
	@Transactional
	@CacheEvict(value = "listaDeTopicos", allEntries = true)
	//Quando esse método for chamado, quero que o Spring limpe aquele cache que chamei de listaDeTopicos
	// limpar todos os registros (allEntries)?  true
	public ResponseEntity<TopicoDto> cadatrar(@RequestBody @Valid TopicoForm form, UriComponentsBuilder uriBuilder) {
//		quando o springo for injeta a classe form, que ela execute as notificações referente a cada atributo anotado
		
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
	
	@GetMapping("/{id}")
	public ResponseEntity<DetalhesTopicoDto> detalhar(@ PathVariable("id") Long id) {
//	 Topico topico=	topicoRepository.getOne(id); // getOnte te devolve o objeto referente ao id, semelhante ao findby
////	Topico topico = topicoRepository.getReferenceById(id);
//	 		return new DetalhesTopicoDto(topico);
		
		Optional<Topico> topico= topicoRepository.findById(id);
		if(topico.isPresent()) {// se o topico estiver resente na lista de topicosret
		return ResponseEntity.ok( new DetalhesTopicoDto(topico.get()) );
		}
		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("/{id}")
	@Transactional // mandar o spring executar o commit/a transação
	@CacheEvict(value = "listaDeTopicos", allEntries = true)
	public ResponseEntity<TopicoDto> atualizar(@PathVariable Long id,  @RequestBody @Valid AtualizacaoTopicoForm form){
		//AtualizacaoTopicoForm define os atributos do Topico que podem ser alterado
		Optional<Topico> optional= topicoRepository.findById(id); // estou fazendo a busca pelo id, se estiver na lista ok, se nao, lanca o codigo 400 notFound
		if(optional.isPresent()) {
			Topico tp=form.atualizar(id, topicoRepository);
			
			return ResponseEntity.ok(new TopicoDto(tp));
		}
		return ResponseEntity.notFound().build();
		
	}
	@DeleteMapping("/{id}")
	@Transactional
	@CacheEvict(value = "listaDeTopicos", allEntries = true)
	public ResponseEntity<?> remover(@PathVariable Long id){
		
		Optional<Topico> optional= topicoRepository.findById(id);
		if(optional.isPresent()) {
			topicoRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
		
	}
	

}
