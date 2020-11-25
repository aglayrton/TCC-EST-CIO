package com.juliao.redacao.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.juliao.redacao.entity.Perfis;
import com.juliao.redacao.entity.Usuarios;
import com.juliao.redacao.repository.UsuarioRepository;

@Service
public class UsuarioService implements UserDetailsService{
	
	@Autowired
	private UsuarioRepository repository;
	
	
	@Transactional(readOnly = true)
	public Usuarios buscarPorEmail(String email) {
		return repository.findByEmail(email);
	}

	//classe do spring para saber se o usuario esta logado ou nao
	@Override @Transactional(readOnly = true) //usamos transactional para que ative a sessão com o banco de dados
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuarios usuario = buscarPorEmail(username);
		return new User(
				usuario.getEmail(), 
				usuario.getSenha(),
				AuthorityUtils.createAuthorityList(getStrings(usuario.getPerfis()))//pois senao aqui não funcionará
		);
	}
	
	//retorna 
	private String[] getStrings(List<Perfis> perfis) {
		String [] authorities = new String[perfis.size()];
		for(int i = 0; i < perfis.size(); i++) {
			authorities[i] = perfis.get(i).getDescricao();
		}
		return authorities;
	}
	
	@Transactional(readOnly = false)
	public void salvarUsuario(Usuarios usuario) {
		String crypt = new BCryptPasswordEncoder().encode(usuario.getSenha());
		usuario.setSenha(crypt);
		repository.save(usuario);
	}

	
	public List<Usuarios> buscarTodos() {
		List<Usuarios> u = repository.findAll();
		return u;
	}

	public Usuarios buscaPorId(Long id) {
		return repository.findById(id).get();
	}

	public void deletar(Long id) {
		repository.deleteById(id);
	}
	
}
