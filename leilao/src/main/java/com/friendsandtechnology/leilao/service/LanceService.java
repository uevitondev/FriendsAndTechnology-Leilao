package com.friendsandtechnology.leilao.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.friendsandtechnology.leilao.model.Lance;
import com.friendsandtechnology.leilao.repository.LanceRepository;

@Service
public class LanceService {

	@Autowired
	private LanceRepository lanceRepository;

	public void salvarLance(Lance lance) {
		lanceRepository.save(lance);
	}

	public List<Lance> buscarTodosLances() {
		return lanceRepository.findAll();
	}

	

}
