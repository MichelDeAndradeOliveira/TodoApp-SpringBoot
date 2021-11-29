package com.michel.todo.controllers;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.michel.todo.config.JwtTokenConfig;
import com.michel.todo.models.Tarefa;
import com.michel.todo.models.Usuario;
import com.michel.todo.repositories.TarefaRepository;
import com.michel.todo.repositories.UsuarioRepository;
import com.michel.todo.services.TarefaService;

@RestController
@RequestMapping("/tarefas")
public class TarefaController {
	private JwtTokenConfig tokenConfig;
	private UsuarioRepository repoUsuario;
	private TarefaRepository repoTarefa;
	private final TarefaService tarefaService;

	public TarefaController(TarefaService tarefaService, UsuarioRepository repoUsuario, TarefaRepository repoTarefa, JwtTokenConfig tokenConfig) {
		this.tarefaService = tarefaService;
		this.repoUsuario = repoUsuario;
		this.repoTarefa = repoTarefa;
		this.tokenConfig = tokenConfig;
	}

	@PostMapping("/add")
	public String addTarefa(@RequestBody Tarefa tarefa,@RequestHeader (name="Authorization") String token) {
		Usuario usuario = repoUsuario.findByEmail(tokenConfig.getUsernameFromToken(token.substring(7)));
		tarefa.setUsuario(usuario);
		tarefaService.addTarefa(tarefa);		
		return "Tarefa adicionada!";
	}

	@PostMapping("/update")
	public String updateTarefa(@RequestBody Tarefa tarefa,@RequestHeader (name="Authorization") String token) {
		Usuario usuario = repoUsuario.findByEmail(tokenConfig.getUsernameFromToken(token.substring(7)));
		if(tarefaService.autorDaTarefa(usuario.getEmail(),tarefa.getId())) {
			tarefaService.updateTarefa(tarefa);
			return "Tarefa alterada com Sucesso";
		}else {
			 return "Esta Tarefa não está vinculada a este Usuário";
		}	
		
	}

	@PostMapping("/concluida")
	public String setTarefaCompletada(@Param(value = "tarefaID") Long tarefaID,@RequestHeader (name="Authorization") String token) {
		Usuario usuario = repoUsuario.findByEmail(tokenConfig.getUsernameFromToken(token.substring(7)));
		Tarefa tarefa = repoTarefa.findById(tarefaID).orElseThrow(() -> new NoSuchElementException());
		if(tarefaService.autorDaTarefa(usuario.getEmail(),tarefa.getId())) {
			tarefa.setCompletada(!tarefa.getCompletada());
			tarefaService.addTarefa(tarefa);
			return "Tarefa marcada como Concluída";
		}else {
			return "Esta Tarefa não está vinculada a este Usuário";
		}				
	}

	@DeleteMapping("/excluir")
	public String deleteTarefa(@Param(value = "tarefaID") Long tarefaID,@RequestHeader (name="Authorization") String token) {
		Usuario usuario = repoUsuario.findByEmail(tokenConfig.getUsernameFromToken(token.substring(7)));
		Tarefa tarefa = repoTarefa.findById(tarefaID).orElseThrow(() -> new NoSuchElementException());
		if(tarefaService.autorDaTarefa(usuario.getEmail(),tarefa.getId())) {
			repoTarefa.delete(tarefa);	
			return "Tarefa Excluída com sucesso";
		}else {
			return "Esta Tarefa não está vinculada a este Usuário";
		}	
	}

	@GetMapping("/listarPorUsuario")
	public List<Tarefa> getTarefasPorUsuario(@Param(value = "prioridade") Integer prioridade,@RequestHeader (name="Authorization") String token) {
		Usuario usuario = repoUsuario.findByEmail(tokenConfig.getUsernameFromToken(token.substring(7)));
		return tarefaService.getTarefasByUsuario(usuario.getId(),prioridade);
	}

}
