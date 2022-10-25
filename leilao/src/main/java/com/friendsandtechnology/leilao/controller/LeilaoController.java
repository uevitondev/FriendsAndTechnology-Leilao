package com.friendsandtechnology.leilao.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.friendsandtechnology.leilao.enums.STATUS;
import com.friendsandtechnology.leilao.model.Lance;
import com.friendsandtechnology.leilao.model.Leilao;
import com.friendsandtechnology.leilao.service.LanceService;
import com.friendsandtechnology.leilao.service.LeilaoService;

@Controller
public class LeilaoController {

    @GetMapping("/cadastroLeilao")
    public ModelAndView cadastro() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("cadastroLeilao");
        return mv;
    }

    @Autowired
    private LeilaoService leilaoService;

    @PostMapping("/cadastroLeilao")
    public String cadastrarLeilao(@RequestParam("image") MultipartFile image, String titulo, String descricao,
            String categoria,
            String valorInicial,
            String lanceMinimo,
            String dataLimite, String vendedor, HttpSession session, RedirectAttributes redirAttrs)
            throws ParseException, IOException {

        if (session.getAttribute("usuarioLogado") == null) {
            redirAttrs.addFlashAttribute("msg", "Deve estar logado para cadastrar um leilão!");
            return "redirect:/cadastroLeilao";

        } else {

            SimpleDateFormat formatador = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat formatadorString = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat formatadorString2 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

            Date data = formatador.parse(dataLimite);
            String data2 = formatadorString.format(data);
            Date date = (Date) formatadorString.parse(data2);
            String stringData = formatadorString2.format(date);

            Date dataAtual = new Date();
            String dataCadastro = formatadorString.format(dataAtual);

            leilaoService
                    .salvarLeilao(new Leilao(null, new Binary(BsonBinarySubType.BINARY, image.getBytes()), titulo,
                            descricao, categoria, Double.parseDouble(valorInicial),
                            Double.parseDouble(lanceMinimo), stringData, vendedor, dataCadastro,
                            STATUS.VIGENTE));

            return "redirect:/leiloes";

        }

    }

    @GetMapping("/leiloes")
    public ModelAndView mostrarLeiloes() throws ParseException {
        List<Leilao> lista = leilaoService.buscarTodosLeiloes();

        SimpleDateFormat formatadorString = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date dataAtual = new Date();
        for (Leilao leilao : lista) {
            if (leilao.getStatus() == STATUS.VIGENTE) {
                String data = leilao.getDataLimite();
                Date dataLimite = formatadorString.parse(data);
                if (dataLimite.before(dataAtual) && leilao.getLances().isEmpty() == true) {
                    leilao.setStatus(STATUS.EXPIRADO);
                    leilaoService.salvarLeilao(leilao);
                } else if (dataLimite.before(dataAtual) && leilao.getLances().isEmpty() == false) {
                    leilao.setStatus(STATUS.ENCERRADO);
                    leilaoService.salvarLeilao(leilao);
                }
            }

        }

        ModelAndView mv = new ModelAndView();
        mv.setViewName("leiloes");
        mv.addObject("lista", lista);
        return mv;
    }

    @GetMapping("/leiloeskeyword")
    public ModelAndView mostrarLeiloesKeword(String keyword, String status) {
        if (keyword == "") {
            List<Leilao> lista = leilaoService.buscarTodosLeiloes();
            ModelAndView mv = new ModelAndView();
            mv.setViewName("leiloes");
            mv.addObject("lista", lista);
            return mv;
        } else {
            List<Leilao> lista = leilaoService.buscarTodosLeiloesByKeyWord(keyword, status);
            ModelAndView mv = new ModelAndView();
            mv.setViewName("leiloes");
            mv.addObject("lista", lista);
            return mv;
        }

    }

    @GetMapping("/leilao")
    public ModelAndView buscaLeilao(@RequestParam String id) {
        Leilao leilao = leilaoService.buscarLeilao(id);
        ModelAndView mv = new ModelAndView();
        mv.setViewName("leilao");
        mv.addObject("leilao", leilao);
        mv.addObject("lances", leilao.getLances());
        mv.addObject("image", Base64.getEncoder().encodeToString(leilao.getImage().getData()));
        return mv;
    }

    @Autowired
    private LanceService lanceService;

    @GetMapping("/darLance")
    public ModelAndView darLanceLeilao(@RequestParam String idLeilao, @RequestParam String valorLance,
            HttpSession session) throws Exception {

        ModelAndView mv = new ModelAndView();
        Leilao leilao = leilaoService.buscarLeilao(idLeilao);

        if (session.getAttribute("usuarioLogado") == null) {

            mv = buscaLeilao(idLeilao);
            mv.setViewName("leilao");
            mv.addObject("msg", "Deve estar logado para dar um lance!");
            return mv;
        }

        if (leilao.getStatus() == STATUS.ENCERRADO || leilao.getStatus() == STATUS.EXPIRADO) {

            mv = buscaLeilao(idLeilao);
            mv.setViewName("leilao");
            mv.addObject("msg", "Não é possível efetuar lances para este leilão!");
            return mv;

        } else if (leilao.getLances().isEmpty()) {
            if (Double.parseDouble(valorLance) <= leilao.getValorInicial()) {

                mv = buscaLeilao(idLeilao);
                mv.setViewName("leilao");
                mv.addObject("msg", "Dê o lance novamente, não deve ser menor ou igual ao lance inicial!");
                return mv;

            } else if (Double.parseDouble(valorLance) <= leilao.getValorInicial() + leilao.getLanceMinimo() - 1) {

                mv = buscaLeilao(idLeilao);
                mv.setViewName("leilao");
                mv.addObject("msg", "Dê o lance novamente, não deve ser menor que o lance mínimo!");
                return mv;

            } else {
                SimpleDateFormat formatadorString = new SimpleDateFormat("dd-MM-yyyy");
                Date dataAtual = new Date();
                String dataCadastro = formatadorString.format(dataAtual);

                Lance saveLance = new Lance(null, Double.parseDouble(valorLance), dataCadastro,
                        session.getAttribute("usuarioLogado").toString());
                lanceService.salvarLance(saveLance);
                leilao.getLances().add(saveLance);
                leilaoService.salvarLeilao(leilao);

                mv = buscaLeilao(idLeilao);
                mv.addObject("ms", "Lance Efetuado!");
                return mv;

            }

        } else {
            List<Lance> lances = leilao.getLances();
            List<Double> listaValores = new ArrayList<>();

            for (Lance x : lances) {
                listaValores.add(x.getValorLance());
            }
            double max = Collections.max(listaValores);

            if (Double.parseDouble(valorLance) <= max + leilao.getLanceMinimo() - 1) {

                mv = buscaLeilao(idLeilao);
                mv.setViewName("leilao");
                mv.addObject("msg", "O lance deve ser igual ou maior que " + (max +
                        leilao.getLanceMinimo()) + " .");
                return mv;

            } else {
                SimpleDateFormat formatadorString = new SimpleDateFormat("dd-MM-yyyy");
                Date dataAtual = new Date();
                String dataCadastro = formatadorString.format(dataAtual);

                Lance saveLance = new Lance(null, Double.parseDouble(valorLance), dataCadastro,
                        session.getAttribute("usuarioLogado").toString());
                lanceService.salvarLance(saveLance);
                leilao.getLances().add(saveLance);
                leilaoService.salvarLeilao(leilao);

                mv = buscaLeilao(idLeilao);
                mv.addObject("ms", "Lance Efetuado!");
                return mv;
            }

        }

    }

}
