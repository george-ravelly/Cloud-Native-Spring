package ufrn.cloud.estoque.estoque;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EstoqueRepository extends JpaRepository<Estoque, Long> {
    Optional<Estoque> findByCodigoProduto(Long cod);

    @Query(
        value = "SELECT * FROM estoque e WHERE e.quantidade > 0",
        nativeQuery = true
    )
    List<Estoque> findAllByQuantidadeMaiorZero();

    boolean existsAllByCodigoProdutoNotIn(List<Long> codes);
}
