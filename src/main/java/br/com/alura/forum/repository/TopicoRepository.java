package br.com.alura.forum.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.alura.forum.modelo.Topico;

public interface TopicoRepository extends JpaRepository<Topico, Long>{

	List<Topico> findByCurso_Nome(String nomeCurso);// onde curso é a classe e nome é o atributo da classe, que esta na classe topicos.
	Page<Topico> findByCurso_Nome(String nomeCurso, Pageable pagicacao);
	
}
