package ufrn.cloud.pedido.venda;

import org.springframework.web.bind.annotation.*;
import ufrn.cloud.pedido.dto.VendaDto;

@RestController
@RequestMapping("/")
public class VendaController {
    private final VendaService service;

    public VendaController(VendaService service) {
        this.service = service;
    }

    @PostMapping
    public VendaDto save(@RequestBody Venda venda) {
        return service.save(venda);
    }

    @PutMapping
    public Venda update(@RequestBody Venda venda) {
        return service.update(venda);
    }

    @GetMapping("/{codigo}")
    public VendaDto getByCodigo(@PathVariable(name = "codigo") Long id) {
        return service.getById(id);
    }
}
