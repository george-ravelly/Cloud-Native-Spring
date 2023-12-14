package ufrn.sistemasdistribuidos.catalogo.produto;

import org.springframework.web.bind.annotation.*;
import ufrn.sistemasdistribuidos.catalogo.dto.ProdutoDTO;

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

    @PutMapping
    public Produto update(@RequestBody Produto produto) {
        return service.update(produto);
    }

    @GetMapping
    public List<Produto> getAll() {
        return service.getAll();
    }

    @GetMapping("/{codigo}")
    public ProdutoDTO getByCod(@PathVariable Long codigo) {
        return service.getById(codigo);
    }

}
