package ufrn.sistemasdistribuidos.catalogo.produto;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produto")
public class ProdutoController {
    private final ProdutoService service;

    public ProdutoController(ProdutoService service) {
        this.service = service;
    }

    @PostMapping
    public Produto save(@RequestBody Produto produto) {
        return service.save(produto);
    }

    @GetMapping
    public List<Produto> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ProdutoDTO getById(@PathVariable Long id) {
        return service.getById(id);
    }

}
