# forum-alura
Essa tem como objetivo ser uma aplicação APIs REST usando Spring Boot de um forum como parte do curso de springboot da alura.
nela, estarei apontando o passo a passo vas implementações e configuraçoes que usarei neste projeto.

## Passo a passo:

- Criei o projeto springboot pelo site URL https://start.spring.io/
- adicionei a módulo chamado "DevTools" para nao ter que ficar atualizando o servidor a cada alteração no projeto, ele fará isso automaticamente.
                 <dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-devtools</artifactId>
			<scope>runtime</scope>
		</dependency>
 - No momento estou trabalhando com o bando de dados h2 e tenho um arquinpo .data na raiz do projeto com instruções para população do banco de dados.
- Como estamos utilizando o Sprig data JPA, criei a interface repository (JpaRepository<T, ID>) para ter acesso ao banco de dados.

- A interface me disponibiliza varios metodos, e caso eu precise de algum que nao tenha por padrão, posso criar meus proprios, ex:

seguindo a convenção do Spring data:

    List<Topico> findByCursoNome(String nomeCurso);


ou, de acordo com minha necessidade e escolha de nome, utilizando o @Query para ensinalo o que fazer:


        @Query("SELECT t FROM Topico t WHERE t.curso.nome = :nomeCurso")
	List<Topico> carregarPorNomeDoCurso(@Param("nomeCurso")String nomeCurso);
	
imports:


import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
