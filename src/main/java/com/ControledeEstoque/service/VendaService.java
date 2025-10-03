package com.ControledeEstoque.service;

import com.ControledeEstoque.model.Produto;
import com.ControledeEstoque.model.Venda;
import com.ControledeEstoque.repository.ProdutoRepository;
import com.ControledeEstoque.repository.VendaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class VendaService {

    private final VendaRepository vendaRepository;
    private final ProdutoRepository produtoRepository;

    public VendaService(VendaRepository vendaRepository, ProdutoRepository produtoRepository) {
        this.vendaRepository = vendaRepository;
        this.produtoRepository = produtoRepository;
    }

    public List<Venda> listarTodas() {
        return vendaRepository.findAll();
    }

    public Optional<Venda> buscarPorId(Long id) {
        return vendaRepository.findById(id);
    }

    public Venda registrarVenda(Venda venda) {
        Produto produto = produtoRepository.findById(venda.getProduto().getId())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        if (produto.getQuantidade() < venda.getQuantidadeVendida()) {
            throw new RuntimeException("Estoque insuficiente");
        }

        produto.setQuantidade(produto.getQuantidade() - venda.getQuantidadeVendida());
        produtoRepository.save(produto);

        if (venda.getDataVenda() == null) {
            venda.setDataVenda(LocalDate.now());
        }

        return vendaRepository.save(venda);
    }

    public void deletar(Long id) {
        vendaRepository.deleteById(id);
    }

    public List<Venda> vendasEntreDatas(LocalDate start, LocalDate end) {
        return vendaRepository.findVendasEntreDatas(start, end);
    }

    public List<Object[]> totalVendidoPorProdutoNative() {
        return vendaRepository.totalVendidoPorProdutoNative();
    }
}
