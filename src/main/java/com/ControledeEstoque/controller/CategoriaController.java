package com.ControledeEstoque.controller;

import com.ControledeEstoque.model.Categoria;
import com.ControledeEstoque.service.CategoriaService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("categorias", categoriaService.listarTodas());
        return "categorias/list";
    }


    @GetMapping("/novo")
    public String novoForm(Model model) {
        model.addAttribute("categoria", new Categoria());
        return "categorias/form";
    }

    @GetMapping("/{id}/editar")
    public String editarForm(@PathVariable Long id, Model model) {
        categoriaService.buscarPorId(id).ifPresent(c -> model.addAttribute("categoria", c));
        return "categorias/form";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Categoria categoria) {
        categoriaService.salvar(categoria);
        return "redirect:/categorias";
    }

    @DeleteMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id) {
        categoriaService.deletar(id);
        return "redirect:/categorias";
    }


    @GetMapping("/buscar")
    public String buscar(@RequestParam("nome") String nome, Model model) {
        model.addAttribute("categorias", categoriaService.buscarPorNome(nome));
        return "categorias/list";
    }
}
