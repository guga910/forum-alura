package br.com.alura.forum.config.validation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErroValidacaoHandler {
	@Autowired
	private MessageSource  messageSource; // alguma coisa sobre receber/ entregar a mensagem no indioma local
	
	@ResponseStatus(code= HttpStatus.BAD_REQUEST) 
//	 O status code padrão a ser devolvido será o 200, mas é possível modificá-lo com a anotação @ResponseStatus.
	// digo para o metodo qual parametro ele vai devolver, qual codigo ele devolverá
	// esse metodo soa como um "tratamento", se nada fosse feito, quando ocorresse este erro, o spring devolveria o codigo 200, que ele entende que o erro foi tratado pelo metodo, essa anotação@ResponseStatus(code= HttpStatus.BAD_REQUEST), diz que o codigo do erro deve continuar a ser mandado
	@ExceptionHandler(MethodArgumentNotValidException.class)
	// sempre que houver essa exception, esse metodo será chamado
	// essa anotação declara qual metodo sera chamado quando houver uma exception.
	public List<ErroFormularioDto> handle(MethodArgumentNotValidException exception) {
		
		List<ErroFormularioDto> dtos= new ArrayList<ErroFormularioDto>();
		
		List<FieldError> fieldErros= exception.getBindingResult().getFieldErrors();// essa variavel devolve os erros do resutado
		fieldErros.forEach(e->{
			String mensagem= messageSource.getMessage(e, LocaleContextHolder.getLocale()); // a memsagem é transformada no idioma local
			ErroFormularioDto erro= new ErroFormularioDto(e.getField(), mensagem);
			dtos.add(erro);
		});
		
		return dtos;
	}
	

}
