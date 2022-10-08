package sistema.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import sistema.model.Contato;
import sistema.model.Professor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    @Autowired
    JdbcTemplate db;

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/contatos")
    public String contatos(Model model) {
        List<Contato> listaDeContatos = db.query(
                "select * from contatos",
                (res, rowNum) -> {
                    Contato contato = new Contato(
                            res.getInt("id"),
                            res.getString("nome"),
                            res.getString("telefone"),
                            res.getString("endereco"));
                    return contato;
                });
        model.addAttribute("contatos", listaDeContatos);
        return "contato";
    }

    @GetMapping("novo")
    public String exibeForm(Model model) {
        model.addAttribute("contato", new Contato());
        return "formulario";
    }

    @PostMapping("novo")
    public String gravaDados(Contato contato) {
        System.out.println("-----------------------");
        System.out.println(contato.getNome());
        System.out.println(contato.getEndereco());
        System.out.println(contato.getTelefone());

        db.update("insert into contatos(endereco, telefone, nome) values (?, ?, ?)",
                contato.getEndereco(), contato.getTelefone(), contato.getNome());
        return "home";
    }

    @GetMapping("/professor")
    public String professores(Model modelo){
        List<Professor> listaDeProfessores = db.query(
            "select * from professores",
            (res, rowNum) -> {
                Professor professor = new Professor(
                        res.getInt("id"),
                        res.getString("nome"));
                return professor;
            });

    modelo.addAttribute("professores", listaDeProfessores);
    return "professor";
    }
    @GetMapping("novoProfessor")
    public String exibeProfessor(Model modelo1){
        modelo1.addAttribute("professor", new Professor());
        return "novoProfessor";
    }
    @PostMapping("novoprofessor")
    public String gravarProfessor(Professor professor){
        db.update("insert into professores (nome) values (?)",professor.getNome());
        return "home";
    }


    @GetMapping("excluir-contato")
    public String apagarContato(@RequestParam(value = "id", required = true)Integer cod, Model model){
        System.out.println("---------------------->"+cod);
        db.update("delete from contatos where id = ?"+cod);
        return "redirect:/contatos";
    }

}
