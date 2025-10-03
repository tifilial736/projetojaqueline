package com.ControledeEstoque.service;

import com.ControledeEstoque.model.Fornecedor;
import com.ControledeEstoque.repository.FornecedorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FornecedorService {
    private final FornecedorRepository repo;

    public FornecedorService(FornecedorRepository repo) {
        this.repo = repo;
    }

    public List<Fornecedor> listarTodos() {
        return repo.findAll();
    }

    public Optional<Fornecedor> buscarPorId(Long id) {
        return repo.findById(id);
    }

    public Fornecedor salvar(Fornecedor fornecedor) {
        return repo.save(fornecedor);
    }

    public void deletar(Long id) {
        repo.deleteById(id);
    }

    // métodos (opcional)
    public List<Fornecedor> buscarPorNome(String nome) {
        return repo.findByNomeContainingIgnoreCase(nome);
    }


}
