package com.juliao.redacao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.juliao.redacao.entity.Perfis;
import com.juliao.redacao.entity.Usuarios;
import com.juliao.redacao.repository.PerfilRepository;
import com.juliao.redacao.service.UsuarioService;

@Controller
@RequestMapping("/plataforma")
public class UsuarioController {
	
	/*DEPÊNDENCIAS*/
	@Autowired
	private UsuarioService service;
	
	@Autowired
	private PerfilRepository perfilRepository;
		
	/*ROTAS HTPP*/
	@GetMapping("/cadastrar")
	public ModelAndView cadastrar() {
		ModelAndView model = new ModelAndView("usuario/cadastrar");
		model.addObject("usuario", new Usuarios());
		return model;
	}
	
	@GetMapping("/visualizar/{id}")
	public String visualizar(@PathVariable("id") Long id, ModelMap model) {
		model.addAttribute("usuario", service.buscaPorId(id));
		return "usuario/listar";
	}
	
	@GetMapping("/listar")
	public String listar(ModelMap model) {
		model.addAttribute("usuarios", service.buscarTodos());
		return "usuario/listar";
	}
	
	/*Consulta externa*/
	@ModelAttribute("perfis")
	public List<Perfis> listaPerfis() {
		return perfilRepository.findAll();
	}
	
	/*CRUD*/
	@PostMapping("/salvar")
	public String salvar(Usuarios usuario) {
		service.salvarUsuario(usuario);
		return "redirect:/plataforma/listar";
	}
	
	@GetMapping("/editar/{id}")
	public String editar(@PathVariable("id") Long id, ModelMap model) {
		model.addAttribute("usuario", service.buscaPorId(id));
		return "usuario/cadastrar";
	}
	
	@GetMapping("/excluir/{id}")
	public String excluir(@PathVariable("id") Long id, ModelMap model) {
		service.deletar(id);
		return "usuario/listar";
	}
}
