package com.michel.todo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.michel.todo.controllers.UsuarioController;
import com.michel.todo.models.Tarefa;
import com.michel.todo.models.Usuario;
import com.michel.todo.services.TarefaService;
import com.michel.todo.services.UsuarioService;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class TodoApplication implements CommandLineRunner{
	
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private UsuarioController usercontrol;
	
	@Autowired
	private TarefaService tarefaService;
	
	public static void main(String[] args) {SpringApplication.run(TodoApplication.class, args);}

	
//	@Bean
//	   public Docket productApi() {
//	      return new Docket(DocumentationType.SWAGGER_2).select()
//	         .apis(RequestHandlerSelectors.basePackage("com.tutorialspoint.swaggerdemo")).build();
//	   }
//	
	@Override
	public void run(String... args) throws Exception {
		System.out.println("-------------------------Iniciado-------------------------");
		Usuario usuario = new Usuario();
		usuario.setNome("Michel de Andrade Oliveira");
		usuario.setSenha("PLACEHOLDER");
		usuario.setEmail("mao97343@gmail.com");
		usuarioService.addUsuario(usuario);
		
		Tarefa todo = new Tarefa();
		todo.setDescricao("TD1");
		todo.setUsuario(usuario);
		
		Tarefa todo2 = new Tarefa();
		todo2.setDescricao("TD2");
		todo2.setUsuario(usuario);
		
		Tarefa todo3 = new Tarefa();
		todo3.setDescricao("TD3");
		todo3.setPrioridade(0);
		todo3.setUsuario(usuario);
		
		Tarefa todo4 = new Tarefa();
		todo4.setDescricao("TD4");
		todo4.setPrioridade(1);
		todo4.setUsuario(usuario);
		tarefaService.addTarefa(todo);
		tarefaService.addTarefa(todo2);
		tarefaService.addTarefa(todo3);
		tarefaService.addTarefa(todo4);
		
		usuario = new Usuario();
		usuario.setNome("Michel");
		usuario.setSenha("PLACEHOLDER");
		usuario.setEmail("mao97343@gmail.com");
		usercontrol.addUsuario(usuario);
	}

}
