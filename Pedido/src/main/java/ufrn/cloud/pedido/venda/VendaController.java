package ufrn.cloud.pedido.venda;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/venda")
public class VendaController {
    private final VendaService service;

    public VendaController(VendaService service) {
        this.service = service;
    }

    @PostMapping
    public Venda save(Venda venda) {
        return service.save(venda);
    }

    @PutMapping
    public Venda update(Venda venda) {
        return service.save(venda);
    }

    @GetMapping("{codigo}")
    public Venda getByCodigo(@PathVariable(name = "codigo") Long id) {
        return service.getById(id);
    }
}
