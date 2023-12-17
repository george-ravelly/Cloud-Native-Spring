package ufrn.cloud.pedido.venda;

import org.springframework.web.bind.annotation.*;
import ufrn.cloud.pedido.itemVenda.ItemVenda;

import java.util.List;

@RestController
@RequestMapping("/")
public class VendaController {
    private final VendaService service;

    public VendaController(VendaService service) {
        this.service = service;
    }

    @PostMapping
    public Venda save(@RequestBody List<ItemVenda> items) {
        return service.save(items);
    }

    @PutMapping
    public Venda update(@RequestBody Venda venda) {
        return service.update(venda);
    }

    @GetMapping("/{codigo}")
    public Venda getByCodigo(@PathVariable(name = "codigo") Long id) {
        return service.getById(id);
    }
}
