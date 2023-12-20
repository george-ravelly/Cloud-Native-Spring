package ufrn.cloud.estoque.estoque;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.stereotype.Service;
import ufrn.cloud.estoque.dto.EstoqueDTO;
import ufrn.cloud.estoque.dto.ProdutoDTO;
import ufrn.cloud.estoque.exceptions.BadRequestException;
import ufrn.cloud.estoque.exceptions.NotFoundException;
import ufrn.cloud.estoque.request.CatalogoWebClient;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class EstoqueService {
    private final EstoqueRepository repository;
    private final CatalogoWebClient catalogoWebClient;
    private final Map<Long, EstoqueDTO> PRODUTOCACHE = new HashMap<>();
    private final Logger logger = Logger.getLogger(EstoqueService.class.getName());

    public EstoqueService(EstoqueRepository repository, CatalogoWebClient catalogoWebClient) {
        this.repository = repository;
        this.catalogoWebClient = catalogoWebClient;
    }

    public Estoque save(Estoque estoque) {
        estoque.setDataCriacao(LocalDateTime.now());
        Optional<Estoque> estoque1 = repository.findByCodigoProduto(estoque.getCodigoProduto());
        if (estoque1.isPresent()) {
            throw new BadRequestException("Produto já esta cadastrado no estoque!");
        }
        return repository.save(estoque);
    }

    public Estoque update(Estoque estoque) {
        Optional<Estoque> estoque1 = repository.findByCodigoProduto(estoque.getCodigoProduto());
        if (estoque1.isEmpty()) {
            throw new BadRequestException("Produto não esta cadastrado no estoque!");
        }
        estoque1.get().setQuantidade(estoque.getQuantidade());
        estoque1.get().setDataModificacao(LocalDateTime.now());
        return repository.save(estoque1.get());
    }

    public Optional<Estoque> updateByIdAndQuantidade(Long id, long quantidade) {
        Optional<Estoque> estoqueOptional = repository.findByCodigoProduto(id);
        if (estoqueOptional.isEmpty()) throw new BadRequestException("Produto não foi encontrado!");
        Estoque estoque = estoqueOptional.get();
        if (quantidade <= 0) throw new BadRequestException("Quantidade de itens muito pequena!");
        if (estoque.getQuantidade() - quantidade > 0) {
            estoque.setQuantidade(estoque.getQuantidade() - quantidade);
            estoque.setDataModificacao(LocalDateTime.now());
            return Optional.of(repository.save(estoque));
        } else {
            throw new BadRequestException("Estoque insuficiente!");
        }
    }

    public List<Estoque> getAll() {
        return repository.findAll();
    }

    public List<Estoque> getAllByQuantidadeMaiorZero() {
        return repository.findAllByQuantidadeMaiorZero();
    }

    @CircuitBreaker(name = "circuitBreakerCatalogo", fallbackMethod = "fallbackCatalogo")
    @Retry(name = "retryCatalogo", fallbackMethod = "fallbackCatalogo")
    @Bulkhead(name = "bulkHeadCatalogo", type = Bulkhead.Type.SEMAPHORE, fallbackMethod = "fallbackCatalogo")
    public EstoqueDTO getByProdutoCod(Long produtoCod) {
        Optional<ProdutoDTO> produto = catalogoWebClient.getProdutoByCod(produtoCod);
        if (produto.isEmpty()) {
            throw new NotFoundException("Produto não encontrado!");
        }

        Optional<Estoque> estoqueOptional = repository.findByCodigoProduto(produto.get().cod());
        if (estoqueOptional.isEmpty()) {
            throw new BadRequestException("Produto fora do estoque!");
        } else {
            EstoqueDTO estoqueDTO = new EstoqueDTO(
                    estoqueOptional.get().getDataModificacao() != null ? estoqueOptional.get().getDataModificacao() : estoqueOptional.get().getDataCriacao(),
                    estoqueOptional.get().getQuantidade(),
                    produto.get()
            );
            logger.log(Level.INFO, "Adicionando produto ao cache local!");
            PRODUTOCACHE.put(produtoCod, estoqueDTO);
            return estoqueDTO;
        }
    }

    public EstoqueDTO fallbackCatalogo(Long codigo, Throwable t) {
        EstoqueDTO produtoDTO = PRODUTOCACHE.getOrDefault(codigo, null);
        if (produtoDTO != null) {
            logger.log(Level.INFO, "Pegando produto do cache local!");
            return produtoDTO;
        }
        throw new NotFoundException("Produdo de código "+ codigo + "não foi encontrado!");
    }

    public boolean hasProdutoEstoque(List<Long> codes) {
        return repository.existsAllByCodigoProdutoNotIn(codes);
    }
}
