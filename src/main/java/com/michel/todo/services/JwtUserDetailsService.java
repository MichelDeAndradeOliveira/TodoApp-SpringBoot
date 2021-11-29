package com.michel.todo.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
    private UsuarioService userService;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		com.michel.todo.models.Usuario user = userService.getByEmail(email);
		System.out.println(user);
		if (user.getEmail().equals(email)) {
			return new User(email, user.getSenha(),
					new ArrayList<>());
		} else {
			throw new UsernameNotFoundException("Usuário registrado com este Email: " + email);
		}
	}
}