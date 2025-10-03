    package com.ControledeEstoque.repository;
    import org.springframework.data.jpa.repository.Query;
    import org.springframework.data.repository.query.Param;
    import com.ControledeEstoque.model.Produto;
    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.data.jpa.repository.Query;
    import org.springframework.data.domain.Pageable;

    import java.util.List;

    public interface ProdutoRepository extends JpaRepository<Produto, Long> {

        // Derived Query 1: buscar por parte do nome (requisito)
        List<Produto> findByNomeContainingIgnoreCase(String nome);

        // Derived Query 2: por quantidade (requisito)
        List<Produto> findByQuantidadeGreaterThan(Integer quantidade);

        // Derived Query extra: preço entre (usado no controller)
        List<Produto> findByPrecoBetween(Double min, Double max);

        // JPQL 1: produtos com estoque abaixo de threshold
        @Query("SELECT p FROM Produto p WHERE p.quantidade < :threshold")
        List<Produto> findProdutosEstoqueBaixo(@org.springframework.data.repository.query.Param("threshold") int threshold);


        // JPQL 2: top 5 mais caros
        @Query("SELECT p FROM Produto p ORDER BY p.preco DESC")
        List<Produto> findTop5MaisCaros(Pageable pageable);

        // JPQL 2: produtos por nome de categoria
        @Query("SELECT p FROM Produto p WHERE p.categoria.nome = :nomeCategoria")
        List<Produto> buscarPorNomeCategoria(String nomeCategoria);
    }
