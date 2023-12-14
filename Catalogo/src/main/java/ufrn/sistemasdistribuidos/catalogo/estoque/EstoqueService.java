package ufrn.sistemasdistribuidos.catalogo.estoque;

import org.springframework.stereotype.Service;
import ufrn.sistemasdistribuidos.catalogo.dto.ProdutoDTO;
import ufrn.sistemasdistribuidos.catalogo.produto.ProdutoService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class EstoqueService {
    private final EstoqueRepository repository;
    private final ProdutoService produtoService;

    public EstoqueService(EstoqueRepository repository, ProdutoService produtoService) {
        this.repository = repository;
        this.produtoService = produtoService;
    }

    public Estoque save(Estoque estoque) {
        estoque.setDataCriacao(LocalDateTime.now());
        return repository.save(estoque);
    }

    public Estoque update(Estoque estoque) {
        estoque.setDataModificacao(LocalDateTime.now());
        return repository.save(estoque);
    }

    public List<Estoque> getAll() {
        return repository.findAll();
    }

    public List<Estoque> getAllByQuantidadeMaiorZero() {
        return repository.findAllByQuantidadeMaiorZero();
    }

    public Estoque getByProdutoCod(Long produtoCod) {
        ProdutoDTO optionalProduto = produtoService.getById(produtoCod);
        Optional<Estoque> estoqueOptional = repository.findByProduto_Cod(optionalProduto.cod());
        if (estoqueOptional.isEmpty()) {
            throw new RuntimeException("Produto fora do estoque!");
        } else {
            return estoqueOptional.get();
        }
    }

    public List<Estoque> searchByNome(String nome) {
        return repository.findAllByProduto_Nome(nome);
    }
}
