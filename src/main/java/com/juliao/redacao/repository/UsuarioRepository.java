package com.juliao.redacao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.juliao.redacao.entity.Usuarios;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuarios, Long> {
	
	@Query("select u from Usuarios u where u.email like :email")
	Usuarios findByEmail(@Param("email") String email);
	
	
}
