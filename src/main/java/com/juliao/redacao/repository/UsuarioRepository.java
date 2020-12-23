package com.juliao.redacao.repository;

import javax.transaction.Transactional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.juliao.redacao.entity.Usuarios;

@Transactional
@Repository
public interface UsuarioRepository extends JpaRepository<Usuarios, Long> {
	
	@Query("select u from Usuarios u where u.email like :email")
	Usuarios findByEmail(@Param("email") String email);
	
	@Query("select u from Usuarios u where u.nome like %?1%")
	Page<Usuarios> findByNomepage(String nome, Pageable page);
	

	//Page<Usuarios> findByNome(String nome, Pageable pageable);
	
}
