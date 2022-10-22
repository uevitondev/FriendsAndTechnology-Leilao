package com.friendsandtechnology.leilao.service;

import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.friendsandtechnology.leilao.exceptions.CripoExistsException;
import com.friendsandtechnology.leilao.exceptions.ServiceException;
import com.friendsandtechnology.leilao.model.Usuario;
import com.friendsandtechnology.leilao.repository.UsuarioRepository;
import com.friendsandtechnology.leilao.util.Util;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public boolean salvarUsuario(Usuario usuarioSave) throws Exception {

        try {
            if (usuarioRepository.findByEmail(usuarioSave.getEmail()) != null) {
                // throw new EmailExistsException("Já existe um email cadastrado para: " +
                // usuarioSave.getEmail());
                return false;
            }
            usuarioSave.setSenha(Util.md5(usuarioSave.getSenha()));

        } catch (NoSuchAlgorithmException e) {
            throw new CripoExistsException("Erro na Criptografia de Senha!");
        }
        usuarioRepository.save(usuarioSave);

        return true;
    }

    public Usuario logarUsuario(String email, String senha) throws ServiceException {
        Usuario usuarioLogin = usuarioRepository.buscarLogin(email, senha);
        return usuarioLogin;
    }

}
