package ufrn.sistemasdistribuidos.catalogo.produto;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/produto")
public class ProdutoController {
    private final ProdutoService service;

    public ProdutoController(ProdutoService service) {
        this.service = service;
    }

    @GetMapping
    public List<Produto> getAll() {
        return List.of();
    }

    @GetMapping("/{id}")
    public Produto getById(@PathVariable Long id) {
        return new Produto();
    }
}
