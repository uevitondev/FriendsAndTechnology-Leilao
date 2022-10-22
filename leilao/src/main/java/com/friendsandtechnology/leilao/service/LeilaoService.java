package com.friendsandtechnology.leilao.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.friendsandtechnology.leilao.model.Leilao;
import com.friendsandtechnology.leilao.repository.LeilaoRepository;

@Service
public class LeilaoService {

    @Autowired
    private LeilaoRepository leilaoRepository;

    public void salvarLeilao(Leilao leilaoSave) {
        leilaoRepository.save(leilaoSave);
    }

    public List<Leilao> buscarTodosLeiloes() {
        return leilaoRepository.findAll();
    }

    public List<Leilao> buscarTodosLeiloesByKeyWord(String keyword, String status) {
        return leilaoRepository.findByKeyWord(keyword, status);
    }

    public Leilao buscarLeilao(String id) {
        Optional<Leilao> leilaoOpt = leilaoRepository.findById(id);
        return leilaoOpt.get();
    }

}
