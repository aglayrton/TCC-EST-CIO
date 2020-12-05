package com.juliao.redacao.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.juliao.redacao.service.UsuarioService;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private UsuarioService service;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		//acessos liberados
		.antMatchers("/sistema/**", "/js/**", "/css/**", "/imagem/**", "/webjars/**", "/webfonts/**").permitAll()
		.antMatchers("/", "/home").permitAll()
		
		//Acessos privados Administrador
		.antMatchers("/usuarios/**").hasAuthority("ADMIN")
		
		//Acessos privados Professores
		.antMatchers("/professores/**").hasAuthority("PROFESSOR")
		
		//Acessos privados Alunos
		.antMatchers("/alunos/**").hasAuthority("ALUNO")
		
		
		.anyRequest().authenticated()
		.and()
			.formLogin()
			.loginPage("/login")
			.defaultSuccessUrl("/", true)
			.failureUrl("/login-error").permitAll()
		.and()
			.logout()
			.logoutSuccessUrl("/login")
		.and()
			.exceptionHandling() //acesso negado
			.accessDeniedPage("/acesso-negado");
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(service).passwordEncoder(new BCryptPasswordEncoder());
	}
	
}
