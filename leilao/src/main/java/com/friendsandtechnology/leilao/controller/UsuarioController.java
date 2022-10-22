package com.friendsandtechnology.leilao.controller;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.friendsandtechnology.leilao.exceptions.ServiceException;
import com.friendsandtechnology.leilao.model.Leilao;
import com.friendsandtechnology.leilao.model.Usuario;
import com.friendsandtechnology.leilao.service.LeilaoService;
import com.friendsandtechnology.leilao.service.UsuarioService;
import com.friendsandtechnology.leilao.util.Util;

@Controller
public class UsuarioController {

    @GetMapping("/cadastroUsuario")
    public ModelAndView cadastro() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("cadastroUsuario");
        return mv;
    }

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/cadastroUsuario")
    public String cadastrarUsuario(String nome, String email, String senha, String cpfCnpj, String dataNascimento,
            String telefone, String endereco, RedirectAttributes redirAttrs) throws Exception {

        boolean salvouSimNao = usuarioService
                .salvarUsuario(new Usuario(null, nome, email, senha, cpfCnpj, dataNascimento, telefone, endereco));

        if (salvouSimNao == false) {
            redirAttrs.addFlashAttribute("msg", "Esse email já está cadastrado!");
            return "redirect:/cadastroUsuario";

        } else {
            redirAttrs.addFlashAttribute("ms", "Usuario Cadastrado com Sucesso!");
            return "redirect:/login";
        }

    }

    @GetMapping("/login")
    public ModelAndView login() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("login");
        return mv;
    }

    @PostMapping("/login")
    public ModelAndView logarUsuario(@RequestParam String email, @RequestParam String senha, HttpSession session)
            throws NoSuchAlgorithmException, ServiceException {

        ModelAndView mv = new ModelAndView();

        Usuario usuarioLogin = usuarioService.logarUsuario(email, Util.md5(senha));

        if (usuarioLogin == null) {
            mv.addObject("msg", "Dados Incorretos - Tente Novamente!");
            return mv;
        } else {

            session.setAttribute("usuarioLogado", usuarioLogin.getNome());
            mv.setViewName("leiloes");

            List<Leilao> lista = leilaoService.buscarTodosLeiloes();
            mv.setViewName("leiloes");
            mv.addObject("lista", lista);
            mv.addObject("ms", "Olá " + usuarioLogin.getNome().toString() + " - Login efetuado com sucesso!");
            mv.addObject("session", session);
            return mv;
        }

    }

    @Autowired
    private LeilaoService leilaoService;

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("usuarioLogado");
        return "redirect:/leiloes";
    }

    @GetMapping("/index")
    public ModelAndView index() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("index");
        return mv;

    }

}
