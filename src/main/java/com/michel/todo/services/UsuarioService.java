package com.michel.todo.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.michel.todo.models.Usuario;
import com.michel.todo.repositories.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired
	private final UsuarioRepository repoUsuario;

	public UsuarioService(UsuarioRepository repoUsuario) {
		this.repoUsuario = repoUsuario;
	}

	public List<Usuario> getAll() {
		List<Usuario> usuarios = new ArrayList<>();
		repoUsuario.findAll().forEach(usuarios::add);
		return usuarios;
	}

	public Optional<Usuario> getUsuario(Long id) {
		return repoUsuario.findById(id);
	}

	public void addUsuario(Usuario usuario) {
		String generatedSecuredPasswordHash = BCrypt.hashpw(usuario.getSenha(), BCrypt.gensalt(12));
		usuario.setSenha(generatedSecuredPasswordHash);
		repoUsuario.save(usuario);
	}
	
	public Usuario getByEmail(String email) {
		return repoUsuario.findByEmail(email);
	}

}
