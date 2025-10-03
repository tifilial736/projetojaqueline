package com.ControledeEstoque.repository;

import com.ControledeEstoque.model.Fornecedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FornecedorRepository extends JpaRepository<Fornecedor, Long> {

    // Derived Query busca fornecedores por parte do nome
    List<Fornecedor> findByNomeContainingIgnoreCase(String nome);


}
