package com.michel.todo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.michel.todo.models.Tarefa;



@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
	
	@Query("SELECT new com.michel.todo.models.Tarefa (t.id, t.descricao, t.completada, t.prioridade, t.usuario) from Tarefa t "
			+ " inner join t.usuario u "
			+ " where t.completada = false and (:prioridade is null or prioridade = :prioridade) and u.id = :usuarioID")
	public List<Tarefa> findByUser(Long usuarioID, Integer prioridade);
	
	@Query("SELECT new com.michel.todo.models.Tarefa (t.id, t.descricao, t.completada, t.prioridade, t.usuario) from Tarefa t "
			+ " inner join t.usuario u "
			+ " where u.email = :email and t.id = :tarefaID ")
	public Tarefa isAutorTarefa(String email, Long tarefaID);
}