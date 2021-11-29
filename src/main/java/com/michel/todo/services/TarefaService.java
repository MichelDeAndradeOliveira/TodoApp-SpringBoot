package com.michel.todo.services;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.michel.todo.models.Tarefa;
import com.michel.todo.repositories.TarefaRepository;

@Service
public class TarefaService {

	private final TarefaRepository repoTodo;

	public TarefaService(TarefaRepository repoTodo) {
		this.repoTodo = repoTodo;
	}

	public List<Tarefa> getTarefas() {
		List<Tarefa> tarefas = new ArrayList<>();

		repoTodo.findAll().forEach(tarefas::add);
		return tarefas;
	}

	public Tarefa getTarefa(Long id) {
		Optional<Tarefa> res = repoTodo.findById(id);
		return res.orElse(null);
	}

	public void addTarefa(Tarefa todo) {
		if (todo.getPrioridade() == null) {
			todo.setPrioridade(2);
		}
		repoTodo.save(todo);
	}

	public void updateTarefa(Tarefa todo) {
		Tarefa td = repoTodo.findById(todo.getId()).orElseThrow(() -> new NoSuchElementException());
		td.setDescricao(todo.getDescricao());
		repoTodo.save(td);
	}

	public void deleteTarefa(Long id) {
		repoTodo.delete(getTarefa(id));
	}

	public List<Tarefa> getTarefasByUsuario(Long usuario,Integer prioridade) {
		return repoTodo.findByUser(usuario,prioridade);
	}
	
	public Boolean autorDaTarefa(String email, Long tarefaID) {
		boolean bool = false;
		Tarefa tarefa = repoTodo.isAutorTarefa(email,tarefaID); 
		if(tarefa != null) {
			bool = true;
		}		
		return bool;
	}

}
