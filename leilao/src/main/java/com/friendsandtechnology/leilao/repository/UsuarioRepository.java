package com.friendsandtechnology.leilao.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.friendsandtechnology.leilao.model.Usuario;

@Repository
public interface UsuarioRepository extends MongoRepository<Usuario, String> {

	String findByEmail(String email);
	
	
	@Query("{ 'email' : ?0, 'senha' : ?1 }")
	Usuario buscarLogin(String email, String senha);

}