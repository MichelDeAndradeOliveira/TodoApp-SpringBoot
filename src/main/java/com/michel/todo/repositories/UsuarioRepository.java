package com.michel.todo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.michel.todo.models.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	@Query(" SELECT u from Usuario u "
			+ " where u.email like :email ")
	public Usuario findByEmail(String email);
}
