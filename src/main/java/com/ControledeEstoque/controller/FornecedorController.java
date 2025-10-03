package com.ControledeEstoque.controller;

import com.ControledeEstoque.model.Fornecedor;
import com.ControledeEstoque.service.FornecedorService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/fornecedores")
public class FornecedorController {

    private final FornecedorService fornecedorService;

    public FornecedorController(FornecedorService fornecedorService) {
        this.fornecedorService = fornecedorService;
    }

    // Listar todos
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("fornecedores", fornecedorService.listarTodos());
        return "fornecedores/list";
    }

    // Form para novo fornecedor
    @GetMapping("/novo")
    public String novoForm(Model model) {
        model.addAttribute("fornecedor", new Fornecedor());
        return "fornecedores/form";
    }

    // Form para editar fornecedor
    @GetMapping("/{id}/editar")
    public String editarForm(@PathVariable Long id, Model model) {
        fornecedorService.buscarPorId(id).ifPresent(f -> model.addAttribute("fornecedor", f));
        return "fornecedores/form";
    }

    // Salvar ou atualizar fornecedor
    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Fornecedor fornecedor) {
        fornecedorService.salvar(fornecedor);
        return "redirect:/fornecedores";
    }

    // Excluir fornecedor com DeleteMapping
    @Transactional
    @DeleteMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id) {
        fornecedorService.deletar(id);
        return "redirect:/fornecedores";
    }


    // Buscar fornecedores por nome
    @GetMapping("/buscar")
    public String buscar(@RequestParam("nome") String nome, Model model) {
        model.addAttribute("fornecedores", fornecedorService.buscarPorNome(nome));
        return "fornecedores/list";
    }


}
