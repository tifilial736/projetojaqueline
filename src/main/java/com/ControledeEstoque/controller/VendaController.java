package com.ControledeEstoque.controller;

import com.ControledeEstoque.model.Produto;
import com.ControledeEstoque.model.Venda;
import com.ControledeEstoque.repository.ProdutoRepository;
import com.ControledeEstoque.service.VendaService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/vendas")
public class VendaController {

    private final VendaService vendaService;
    private final ProdutoRepository produtoRepo;

    public VendaController(VendaService vendaService, ProdutoRepository produtoRepo) {
        this.vendaService = vendaService;
        this.produtoRepo = produtoRepo;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("vendas", vendaService.listarTodas());
        return "vendas/list";
    }

    @GetMapping("/nova")
    public String novaForm(Model model) {
        model.addAttribute("venda", new Venda());
        model.addAttribute("produtos", produtoRepo.findAll());
        return "vendas/form";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Venda venda,
                         @RequestParam Long produtoId,
                         Model model) {
        try {
            Produto produto = produtoRepo.findById(produtoId)
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

            venda.setProduto(produto);

            if (venda.getDataVenda() == null) {
                venda.setDataVenda(LocalDate.now());
            }

            vendaService.registrarVenda(venda);
            return "redirect:/vendas";

        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("venda", venda);
            model.addAttribute("produtos", produtoRepo.findAll());
            return "vendas/form";
        }
    }

    @Transactional
    @DeleteMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id) {
        vendaService.deletar(id);
        return "redirect:/vendas";
    }

    @GetMapping("/relatorios")
    public String relatorios(@RequestParam(required = false) String start,
                             @RequestParam(required = false) String end,
                             Model model) {
        if (start != null && end != null) {
            LocalDate s = LocalDate.parse(start);
            LocalDate e = LocalDate.parse(end);
            model.addAttribute("vendas", vendaService.vendasEntreDatas(s, e));
        }
        model.addAttribute("totais", vendaService.totalVendidoPorProdutoNative());
        return "vendas/relatorios";
    }

    @GetMapping("/total-por-produto")
    public String totalPorProduto(Model model) {
        model.addAttribute("totais", vendaService.totalVendidoPorProdutoNative());
        return "vendas/relatorios";
    }
}
