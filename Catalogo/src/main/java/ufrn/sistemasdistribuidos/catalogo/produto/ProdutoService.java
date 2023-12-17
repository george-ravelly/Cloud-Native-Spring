package ufrn.sistemasdistribuidos.catalogo.produto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ufrn.sistemasdistribuidos.catalogo.dto.MarcaDTO;
import ufrn.sistemasdistribuidos.catalogo.dto.ProdutoDTO;
import ufrn.sistemasdistribuidos.catalogo.exceptions.BadRequestException;
import ufrn.sistemasdistribuidos.catalogo.exceptions.NotFoundException;

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
        if (produto.getCod() == null) {
            throw new BadRequestException("Código do produto não informado!");
        }
        produto.setDataModificacao(LocalDateTime.now());
        return repository.save(produto);
    }

    public List<Produto> getAll() {
        return repository.findAll();
    }

    public ProdutoDTO getById(Long id) {
        Optional<Produto> produtoOptional = repository.findById(id);
        if (produtoOptional.isEmpty()) {
            throw new NotFoundException("Produto não encontrado!");
        } else {
            return new ProdutoDTO(
                    produtoOptional.get().getCod(),
                    produtoOptional.get().getNome(),
                    produtoOptional.get().getDescricao(),
                    produtoOptional.get().getPreco(),
                    new MarcaDTO(
                            produtoOptional.get().getMarca().getCod(),
                            produtoOptional.get().getMarca().getNome()
                    )
            );
        }
    }
}
