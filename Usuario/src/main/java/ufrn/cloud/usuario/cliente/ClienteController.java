package ufrn.cloud.usuario.cliente;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cliente")
public class ClienteController {
    private final ClienteService service;

    public ClienteController(ClienteService service) {
        this.service = service;
    }

    @PostMapping
    public Cliente save(@RequestBody Cliente cliente) {
        return service.save(cliente);
    }

    @PutMapping
    public Cliente update(@RequestBody Cliente cliente) {
        return service.update(cliente);
    }

    @GetMapping("/{id}")
    public Cliente getById(@PathVariable("id") Long id) {
        return service.getById(id);
    }

    @GetMapping("/existe/{id}")
    public boolean clienteExiste(@PathVariable("id") Long id) {
        return service.clienteExiste(id);
    }
}
