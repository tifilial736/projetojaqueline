package com.ControledeEstoque.service;

import com.ControledeEstoque.model.Categoria;
import com.ControledeEstoque.repository.CategoriaRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {
    private final CategoriaRepository categoriaRepository;
    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }
    public List<Categoria> buscarPorNome(String nome) {
        return categoriaRepository.findByNomeContainingIgnoreCase(nome);
    }
    public List<Categoria> listarTodas() { return categoriaRepository.findAll(); }
    public Optional<Categoria> buscarPorId(Long id) { return categoriaRepository.findById(id); }
    public Categoria salvar(Categoria c) { return categoriaRepository.save(c); }
    public void deletar(Long id) { categoriaRepository.deleteById(id); }
}
