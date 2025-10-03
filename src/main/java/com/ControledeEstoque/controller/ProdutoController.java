package com.ControledeEstoque.controller;

import com.ControledeEstoque.model.Produto;
import com.ControledeEstoque.service.CategoriaService;
import com.ControledeEstoque.service.ProdutoService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;
    private final CategoriaService categoriaService;

    public ProdutoController(ProdutoService produtoService, CategoriaService categoriaService) {
        this.produtoService = produtoService;
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public String listar(@RequestParam(required = false) String nome,
                         @RequestParam(required = false) Double min,
                         @RequestParam(required = false) Double max,
                         @RequestParam(required = false) String categoriaNome,
                         Model model) {

        if (nome != null && !nome.isBlank()) {
            model.addAttribute("produtos", produtoService.buscarPorNome(nome));
        } else if (min != null && max != null) {
            model.addAttribute("produtos", produtoService.buscarPorFaixaPreco(min, max));
        } else if (categoriaNome != null && !categoriaNome.isBlank()) {
            model.addAttribute("produtos", produtoService.buscarPorCategoria(categoriaNome));
        } else {
            model.addAttribute("produtos", produtoService.listarTodos());
        }

        model.addAttribute("categorias", categoriaService.listarTodas());
        return "produtos/list";
    }

    @GetMapping("/novo")
    public String novoForm(Model model) {
        model.addAttribute("produto", new Produto());
        model.addAttribute("categorias", categoriaService.listarTodas());
        return "produtos/form";
    }

    @GetMapping("/{id}/editar")
    public String editarForm(@PathVariable Long id, Model model) {
        produtoService.buscarPorId(id).ifPresent(p -> {
            model.addAttribute("produto", p);
            model.addAttribute("categorias", categoriaService.listarTodas());
        });
        return "produtos/form";
    }
    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Produto produto) {
        if (produto.getCategoria() != null && produto.getCategoria().getId() != null) {
            categoriaService.buscarPorId(produto.getCategoria().getId())
                    .ifPresent(produto::setCategoria);
        }
        produtoService.salvar(produto);
        return "redirect:/produtos";
    }


    @Transactional
    @DeleteMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id) {
        produtoService.deletar(id);
        return "redirect:/produtos";
    }


    // Estoque baixo (JPQL)
    @GetMapping("/estoque-baixo")
    public String estoqueBaixo(@RequestParam(defaultValue = "5") int threshold, Model model) {
        model.addAttribute("produtos", produtoService.produtosEstoqueBaixo(threshold));
        model.addAttribute("categorias", categoriaService.listarTodas());
        return "produtos/list";
    }

    // Top 5 mais caros (JPQL)
    @GetMapping("/top-caros")
    public String topCaros(Model model) {
        model.addAttribute("produtos", produtoService.findTop5MaisCaros());
        model.addAttribute("categorias", categoriaService.listarTodas());
        return "produtos/list";
    }
}
