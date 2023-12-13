package ufrn.sistemasdistribuidos.catalogo.produto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {
    private final ProdutoRepository repository;

    @Autowired
    public ProdutoService(ProdutoRepository repository) {
        this.repository = repository;
    }

    public Produto save (Produto produto) {
        produto.setDataCriacao(LocalDateTime.now());
        return repository.save(produto);
    }

    public Produto update (Produto produto) {
        produto.setDataModificacao(LocalDateTime.now());
        return repository.save(produto);
    }

    public List<Produto> getAll() {
        return repository.findAll();
    }

    public ProdutoDTO getById(Long id) {
        Optional<Produto> produtoOptional = repository.findById(id);
        if (produtoOptional.isEmpty()) {
            throw new RuntimeException("Produto não encontrado!");
        } else {
            return new ProdutoDTO(
                    produtoOptional.get().getNome(),
                    produtoOptional.get().getDescricao(),
                    produtoOptional.get().getPreco()
            );
        }
    }
}
