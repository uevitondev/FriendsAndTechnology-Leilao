package com.friendsandtechnology.leilao.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.friendsandtechnology.leilao.model.Leilao;

@Repository
public interface LeilaoRepository extends MongoRepository<Leilao, String> {

    @Query("{ 'id' : ?0 }")
    Optional<Leilao> findById(String id);

    @Query("{ 'categoria' : ?0, 'status': ?1}")
    List<Leilao> findByKeyWord(String keyword, String status);

}
