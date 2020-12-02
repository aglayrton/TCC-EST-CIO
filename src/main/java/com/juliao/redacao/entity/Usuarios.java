package com.juliao.redacao.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "usuarios", indexes= {@Index(name = "idx_usuario_email", columnList="email")})
public class Usuarios implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "nome", unique = true, nullable=false)
	private String nome;
	
	@Column(name = "email", unique = true, nullable=false)
	private String email;
	
	@Column(name = "senha", nullable = false)
	private String senha;
	
	@ManyToMany
	@JoinTable(
			name = "usuarios_tem_perfis",
			joinColumns = {@JoinColumn(name = "usuario_id", referencedColumnName = "id", table = "usuarios")},
			inverseJoinColumns = {@JoinColumn(name = "perfil_id", referencedColumnName = "id", table = "perfis")}
	)
	private List<Perfis> perfis;
	
	@Column(name = "ativo", nullable = false, columnDefinition="TINYINT(1)")
	private boolean ativo;
	
	@Column(name = "codigo_verificador", length = 6)
	private String codigoVerificador;
	
	
	/*CONSTRUTORES*/
	public Usuarios() {
		super();
	}
	
	public Usuarios(Long id) {
		setId(id);
	}
	
	//ADICIONAR PERFIS A LISTA
	/*public void addPerfil(PerfilTipo tipo) {
		if(this.perfis == null) {
			this.perfis = new ArrayList<>();
		}
		this.perfis.add(new Perfis(tipo.getCod()));
	}*/
	
	
	//////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public String getEmail() {
		return email;
	}

	public String getSenha() {
		return senha;
	}

	public List<Perfis> getPerfis() {
		return perfis;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public String getCodigoVerificador() {
		return codigoVerificador;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public void setPerfis(List<Perfis> perfis) {
		this.perfis = perfis;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public void setCodigoVerificador(String codigoVerificador) {
		this.codigoVerificador = codigoVerificador;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuarios other = (Usuarios) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
}
