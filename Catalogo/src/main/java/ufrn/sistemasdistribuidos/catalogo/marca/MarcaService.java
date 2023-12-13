package ufrn.sistemasdistribuidos.catalogo.marca;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MarcaService {
    private final MarcaRepository repository;

    public MarcaService(MarcaRepository repository) {
        this.repository = repository;
    }

    public Marca save(Marca marca) {
        return repository.save(marca);
    }

    public List<Marca> getAll() {
        return repository.findAll();
    }

    public Marca getByCod(Long cod) {
        return repository.getReferenceById(cod);
    }

    public void saveAll(List<Marca> marcaList) {
        repository.saveAll(marcaList);
    }
}
