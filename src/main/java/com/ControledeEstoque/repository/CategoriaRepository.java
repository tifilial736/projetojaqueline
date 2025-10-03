package com.ControledeEstoque.repository;

import com.ControledeEstoque.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    // Derived Query
    List<Categoria> findByNomeContainingIgnoreCase(String nome);
    List<Categoria> findByNome(String nome);
}
