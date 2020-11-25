package com.juliao.redacao.entity;

public enum PerfilTipo {
	ADMIN(1, "ADMIN"), PROFESSOR(2, "PROFESSOR"), ALUNO(3, "ALUNO");
	
	private long cod;
	private String descricao;

	private PerfilTipo(long cod, String desc) {
		this.cod = cod; 
		this.descricao = desc;
	}

	public long getCod() {
		return cod;
	}

	public String getDescricao() {
		return descricao;
	}
	
	
}
