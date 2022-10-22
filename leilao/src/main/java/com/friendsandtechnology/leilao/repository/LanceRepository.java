package com.friendsandtechnology.leilao.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.friendsandtechnology.leilao.model.Lance;

@Repository
public interface LanceRepository extends MongoRepository<Lance, String> {
}
