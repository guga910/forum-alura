package br.com.alura.forum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@SpringBootApplication
@EnableSpringDataWebSupport //Paginação: essa anotação habilita o spring pegar os dados de paginação e ordenaççao e mandar parao spring data
@EnableCaching // habilitao uso do cache na aplicação
public class ForumApplication {

	public static void main(String[] args) {
		SpringApplication.run(ForumApplication.class, args);
		
//		https://cursos.alura.com.br/course/spring-boot-seguranca-cache-monitoramento/task/55842
//			
//			assite ai pra tentar logar direito..ai. 
//			:/
		System.out.println("\nFoi?");
	}

}
