package com.ControledeEstoque.repository;

import com.ControledeEstoque.model.Venda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.time.LocalDate;
import java.util.List;

public interface VendaRepository extends JpaRepository<Venda, Long> {

    // Derived Query (vendas entre datas) - também atende requisito JPQL se preferir
    List<Venda> findByDataVendaBetween(LocalDate inicio, LocalDate fim);

    // JPQL (requisito) - exemplo alternativo
    @Query("SELECT v FROM Venda v WHERE v.dataVenda BETWEEN :start AND :end")
    List<Venda> findVendasEntreDatas(LocalDate start, LocalDate end);

    // Native Query (requisito): total vendido por produto (nome + soma)
    @Query(value = "SELECT p.nome as produto_nome, SUM(v.quantidade_vendida) as total_vendido " +
            "FROM venda v JOIN produto p ON v.produto_id = p.id " +
            "GROUP BY p.nome", nativeQuery = true)
    List<Object[]> totalVendidoPorProdutoNative();
}