package com.juliao.redacao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.juliao.redacao.entity.Perfis;
import com.juliao.redacao.entity.Usuarios;
import com.juliao.redacao.repository.PerfilRepository;
import com.juliao.redacao.repository.UsuarioRepository;
import com.juliao.redacao.service.UsuarioService;


@Controller
@RequestMapping("/usuarios")
public class UsuarioController {
	
	/*DEPÊNDENCIAS*/
	@Autowired
	private UsuarioService service;
	
	@Autowired
	private UsuarioRepository repository;
	
	@Autowired
	private PerfilRepository perfilRepository;
		
	/*ROTAS HTPP*/
	@GetMapping("/cadastrar")
	public ModelAndView cadastrar(Usuarios usuarios) {
		ModelAndView model = new ModelAndView("usuario/cadastrar");
		model.addObject("usuarios", new Usuarios());
		return model;
	}
	
	@GetMapping("/visualizar/{id}")
	public String visualizar(@PathVariable("id") Long id, ModelMap model) {
		model.addAttribute("usuario", service.buscaPorId(id));
		return "usuario/listar";
	}
	
	/*@GetMapping("/listar")
	public String listar(ModelMap model) {
		model.addAttribute("usuarios", repository.findAll(PageRequest.of(0, 2, Sort.by("nome"))));
		return "usuario/listar";
	}*/
	
	@GetMapping("/listar")
	public String carregarPessoaPorPaginacao(@PageableDefault(size = 2) Pageable pageable, ModelMap model) {
		Page<Usuarios> pagePessoa = repository
				.findAll(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("nome")));
		model.addAttribute("usuarios", pagePessoa);
		return "usuario/listar";
	}
	
	/*Consulta externa*/
	@ModelAttribute("perfis")
	public List<Perfis> listaPerfis() {
		return perfilRepository.findAll();
	}
	
	/*CRUD*/
	@PostMapping("/salvar")
	public String salvar(Usuarios usuarios, RedirectAttributes attr) {
		try {
			service.salvarUsuario(usuarios);
			attr.addFlashAttribute("success", "Operação realizada com sucesso!");
			return "redirect:/usuarios/listar";
		}catch(DataIntegrityViolationException e) {
			attr.addFlashAttribute("fail", "Cadastro não realizado, email já existente.");
			return "redirect:/usuarios/cadastrar";
		}
		
	}
	
	@PostMapping("/editar")
	public String ediatr(Usuarios usuarios, RedirectAttributes attr) {
		try {
			service.salvarUsuario(usuarios);
			attr.addFlashAttribute("success", "Edição realizada com sucesso!");
			return "redirect:/usuarios/listar";
		}catch(DataIntegrityViolationException e) {
			attr.addFlashAttribute("fail", "Edição não realizada, email já existente.");
			return "redirect:/usuarios/cadastrar";
		}
		
	}
	
	@GetMapping("/preeditar/{id}")
	public String preEditar(@PathVariable("id") Long id, ModelMap model) {
		model.addAttribute("usuarios", service.buscaPorId(id));
		return "usuario/cadastrar";
	}
	
	@GetMapping("/excluir/{id}")
	public String excluir(@PathVariable("id") Long id, RedirectAttributes attr) {
		service.deletar(id);
		attr.addFlashAttribute("success", "Exclusão realizada com sucesso!");
		return "redirect:/usuarios/listar";
	}
	
	@PostMapping("/buscar")
	public ModelAndView consultar(
			@RequestParam("nomepesquisa") String nome, 
			@PageableDefault(size = 5, sort = {"nome"}) Pageable pageable
	) {
		ModelAndView model = new ModelAndView("usuario/listar");
		
		Page<Usuarios> usuarios = repository.findByNomepage(nome, pageable);
		model.addObject("usuarios", usuarios);
		return model;
	}
	
	/*@PostMapping("/buscar")
	public ModelAndView consultar(
			@RequestParam("nomepesquisa") String nome, 
			@PageableDefault(size = 5, sort = {"nome"}) Pageable pageable
	) {
		ModelAndView model = new ModelAndView("usuario/listar");
		
		List<Usuarios> usuarios = service.buscarPorNome(nome);
		model.addObject("usuarios", usuarios);
		return model;
	}*/
	
}
