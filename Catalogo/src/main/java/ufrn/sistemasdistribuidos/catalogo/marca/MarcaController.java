package ufrn.sistemasdistribuidos.catalogo.marca;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/marca")
public class MarcaController {
    private final MarcaService service;

    public MarcaController(MarcaService service) {
        this.service = service;
    }

    @PostMapping
    public Marca save (@RequestBody Marca marca) {
        return service.save(marca);
    }

    @GetMapping
    public List<Marca> listarTodos() {
        return service.getAll();
    }

    @GetMapping("/cod")
    public Marca buscarPorCodigo (@PathVariable Long cod) {
        return service.getByCod(cod);
    }
}
