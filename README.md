
## PASSO A PASSO:


### CRUD:


Como estamos utilizando o Sprig data JPA, criei a interface repository (JpaRepository<T, ID>) para ter acesso ao banco de dados.


- A interface me disponibiliza vários métodos, e caso eu precise de algum que não tenha por padrão, posso criar meus próprios, ex:

- List<Topico> findByCursoNome(String nomeCurso);// seguindo a convenção do Spring data
ou, de acordo com minha necessidade e escolha de nome, utilizando o @Query para ensiná-lo o que fazer:
        
	@Query("SELECT t FROM Topico t WHERE t.curso.nome = :nomeCurso")
List<Topico> carregarPorNomeDoCurso(@Param("nomeCurso")String nomeCurso);
	
imports:
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

- Crie a classe TopicoForm que será responsável por receber os dados digitados pelo cliente para que depois possam ser convertidos para a classe Topico, e assim, a estrutura da nossa entidade não seja esposta.

- Na classe TopicoController , no método cadastrar eu retomei um  ResponseEntity, para receber o código especifico da criação de um objeto no banco de dados(201), caso contrario, só teria recebido o codigo 200 que somente diz que a chamada foi concluiada.
	
	Para isso, precisei receber uma  UriComponentsBuilder como parâmetro, dentro do método precisei criar uma URI Local com o nome do topico 
	
	(URI uri= uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();) 
		
	return ResponseEntity.created(uri).body(new TopicoDto(topico));


### VALIDAÇÕES:

- anotações Bean Validation do pacote javax.validation.constraints.
	
Ex: @NotNull @NotEmpty @Length(min = 5)
	
- No corpo do método, tenho que chamar a anotação @Valid para dizer ao spring, que quando o metodo for executado, deve ser verificado as anotações de validações que constem na classe, e que foram devidamente anotadas.
	
Ex: public ResponseEntity<TopicoDto> cadatrar(@RequestBody @Valid TopicoForm form, UriComponentsBuilder uriBuilder){};
	
- Se os valores não corresponderem com a validação, a chamada do método gerará o erro 400.

-Dependencia:
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
</dependency>

####  ErroValidacaoHandler:


- Criei uma classe interceptadora de exceptions, que sempre que uma exception especifica acontecer de qualquer método de qualquer controller ele sera chamado.
	
- @ExceptionHandler(MethodArgumentNotValidException.class)- Sempre que houver a exception MethodArgumentNotValidException essa anotação chamara seu método subordinado;
	
- Apesar de estarmos fazendo um tratamento de erro, a anotação @ResponseStatus(code= HttpStatus.BAD_REQUEST) fara com que  a exception mantenha seus posição negativo (cod 400);
	
- Dai fiz uma lista com o erro lançado e a mensagem do erro e entreguei.
	
- Analisar as classes: ErroValidacaoHandler e ErroFormularioDto no pacote br.com.alura.forum.config.validation;
