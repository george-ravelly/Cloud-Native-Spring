package ufrn.cloud.estoque.estoque;

import org.springframework.web.bind.annotation.*;
import ufrn.cloud.estoque.dto.EstoqueDTO;

import java.util.List;

@RestController
@RequestMapping("/produto")
public class EstoqueController {
    private final EstoqueService service;

    public EstoqueController(EstoqueService service) {
        this.service = service;
    }

    @PostMapping
    public Estoque save(@RequestBody Estoque estoque) {
        return service.save(estoque);
    }

    @PutMapping
    public Estoque update(@RequestBody Estoque estoque) {
        return service.update(estoque);
    }

    @GetMapping
    public List<Estoque> listarProdutosEmEstoque (
            @RequestParam(name = "emEstoque", required = false, defaultValue = "false") Boolean emEstoque
    ) {
        if (emEstoque) return service.getAllByQuantidadeMaiorZero();
        else return service.getAll();
    }

    @GetMapping("/{cod}")
    public EstoqueDTO getByProdutoCod(@PathVariable Long cod) {
        return service.getByProdutoCod(cod);
    }
}
