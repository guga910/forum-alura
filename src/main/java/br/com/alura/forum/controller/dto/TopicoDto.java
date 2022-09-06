package br.com.alura.forum.controller.dto;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;

import br.com.alura.forum.modelo.Topico;

public class TopicoDto {
	
	private Long id;
	private String titulo;
	private String mensagem;
	private LocalDateTime dataCriacao;
	
	public TopicoDto(Topico topico) {

		this.id= topico.getId();
		this.titulo= topico.getTitulo();
		this.mensagem= topico.getMensagem();
		this.dataCriacao= topico.getDataCriacao();
	}
	
	public Long getId() {
		return id;
	}
	public String getTitulo() {
		return titulo;
	}
	public String getDuvida() {
		return mensagem;
	}
	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}

//	public static List<TopicoDto> converter(List<Topico> topicos) {
	public static Page<TopicoDto> converter(Page<Topico> topicos) {
		// TODO Auto-generated method stub
//		List<TopicoDto> tdos= new ArrayList<TopicoDto>();
//		topicos.forEach(t->{
//			TopicoDto dto= new TopicoDto(t);
//			tdos.add(dto);
//		});
//		return tdos;
//		return topicos.stream().map(TopicoDto::new).collect(Collectors.toList());
		return topicos.map(TopicoDto::new);
		/* ele vai receber uma lista de topicos, vai passar pelo contrutor do topicoDto
		 * que recebe um topico e cria um topicoDto e vai esta transformando isso em uma List.
		 */
		
	}
	
	

}
