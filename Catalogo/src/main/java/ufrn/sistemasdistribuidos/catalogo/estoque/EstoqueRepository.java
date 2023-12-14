package ufrn.sistemasdistribuidos.catalogo.estoque;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EstoqueRepository extends JpaRepository<Estoque, Long> {
    Optional<Estoque> findByProduto_Cod(Long cod);

    @Query(
        value = "SELECT * FROM estoque e WHERE e.quantidade > 0",
        nativeQuery = true
    )
    List<Estoque> findAllByQuantidadeMaiorZero();

    List<Estoque> findAllByProduto_Nome(@Param("nome") String nome);
}
