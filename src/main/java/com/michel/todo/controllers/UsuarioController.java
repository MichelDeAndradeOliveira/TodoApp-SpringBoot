package com.michel.todo.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.michel.todo.models.Usuario;
import com.michel.todo.services.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
	private UsuarioService usuarioService;

	public UsuarioController(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	@PostMapping("/new")
	public String addUsuario(@RequestBody Usuario usuarioRequest) throws Exception {
		Usuario usuario = new Usuario();
		String errors="";

		if (usuarioRequest.getNome() == null)
			errors.concat("Nome não informado. ");
		if (usuarioRequest.getEmail() == null)
			errors.concat("Email não informado. ");
		if (usuarioRequest.getSenha() == null)
			errors.concat("Senha não informada.");

		if (usuarioService.getByEmail(usuarioRequest.getEmail()) != null)
			errors += ("Já existe um Usuário com este Email. ");

		if (!errors.isEmpty()) {
			System.out.println(errors);
			return errors;
		} else {
			usuario.setNome(usuarioRequest.getNome());
			usuario.setEmail(usuarioRequest.getEmail());
			usuario.setSenha(usuarioRequest.getSenha());
			usuarioService.addUsuario(usuario);
			return "Novo usuário salvo com sucesso";
		}
	}
}
