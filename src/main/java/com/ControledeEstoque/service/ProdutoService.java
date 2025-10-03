package com.ControledeEstoque.service;

import com.ControledeEstoque.model.Produto;
import com.ControledeEstoque.repository.ProdutoRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.Optional;

@Service
public class ProdutoService {
    private final ProdutoRepository produtoRepository;
    public ProdutoService(ProdutoRepository produtoRepository) { this.produtoRepository = produtoRepository; }

    public List<Produto> listarTodos() { return produtoRepository.findAll(); }
    public Optional<Produto> buscarPorId(Long id) { return produtoRepository.findById(id); }
    public Produto salvar(Produto p) { return produtoRepository.save(p); }
    public void deletar(Long id) { produtoRepository.deleteById(id); }

    // wrappers para queries custom
    public List<Produto> buscarPorNome(String nome) { return produtoRepository.findByNomeContainingIgnoreCase(nome); }
    public List<Produto> buscarPorFaixaPreco(Double min, Double max) { return produtoRepository.findByPrecoBetween(min, max); }
    public List<Produto> findTop5MaisCaros() {
        Pageable top5 = PageRequest.of(0, 5);
        return produtoRepository.findTop5MaisCaros(top5);
    }
    public List<Produto> produtosEstoqueBaixo(int threshold) { return produtoRepository.findProdutosEstoqueBaixo(threshold); }
    public List<Produto> buscarPorCategoria(String nomeCategoria) { return produtoRepository.buscarPorNomeCategoria(nomeCategoria); }
}

