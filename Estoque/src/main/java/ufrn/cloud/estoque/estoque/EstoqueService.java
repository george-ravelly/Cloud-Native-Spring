package ufrn.cloud.estoque.estoque;

import org.springframework.stereotype.Service;
import ufrn.cloud.estoque.dto.EstoqueDTO;
import ufrn.cloud.estoque.dto.ProdutoDTO;
import ufrn.cloud.estoque.request.CatalogoWebClient;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class EstoqueService {
    private final EstoqueRepository repository;
    private final CatalogoWebClient catalogoWebClient;

    public EstoqueService(EstoqueRepository repository, CatalogoWebClient catalogoWebClient) {
        this.repository = repository;
        this.catalogoWebClient = catalogoWebClient;
    }

    public Estoque save(Estoque estoque) {
        estoque.setDataCriacao(LocalDateTime.now());
        Optional<Estoque> estoque1 = repository.findByCodigoProduto(estoque.getCodigoProduto());
        if (estoque1.isPresent()) {
            throw new RuntimeException("Produto já esta cadastrado no estoque!");
        }
        return repository.save(estoque);
    }

    public Estoque update(Estoque estoque) {
        Optional<Estoque> estoque1 = repository.findByCodigoProduto(estoque.getCodigoProduto());
        if (estoque1.isEmpty()) throw new RuntimeException("Produto não esta cadastrado no estoque!");
        estoque1.get().setQuantidade(estoque.getQuantidade());
        estoque1.get().setDataModificacao(LocalDateTime.now());
        return repository.save(estoque1.get());
    }

    public List<Estoque> getAll() {
        return repository.findAll();
    }

    public List<Estoque> getAllByQuantidadeMaiorZero() {
        return repository.findAllByQuantidadeMaiorZero();
    }

    public EstoqueDTO getByProdutoCod(Long produtoCod) {
        Optional<ProdutoDTO> produto = catalogoWebClient.getProdutoByCod(produtoCod);
        if (produto.isEmpty()) {
            throw new RuntimeException("Produto não encontrado!");
        }

        Optional<Estoque> estoqueOptional = repository.findByCodigoProduto(produto.get().cod());
        if (estoqueOptional.isEmpty()) {
            throw new RuntimeException("Produto fora do estoque!");
        } else {
            return new EstoqueDTO(
                    estoqueOptional.get().getDataModificacao() != null ? estoqueOptional.get().getDataModificacao() : estoqueOptional.get().getDataCriacao(),
                    estoqueOptional.get().getQuantidade(),
                    produto.get()
            );
        }
    }

    public boolean hasProdutoEstoque(List<Long> codes) {
        return repository.existsAllByCodigoProdutoNotIn(codes);
    }
}
