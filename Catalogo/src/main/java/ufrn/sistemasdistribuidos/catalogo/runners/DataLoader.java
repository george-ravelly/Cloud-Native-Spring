package ufrn.sistemasdistribuidos.catalogo.runners;

import org.springframework.boot.CommandLineRunner;
import ufrn.sistemasdistribuidos.catalogo.marca.Marca;
import ufrn.sistemasdistribuidos.catalogo.marca.MarcaService;

import java.util.ArrayList;
import java.util.List;

public class DataLoader implements CommandLineRunner {
    private final MarcaService service;

    public DataLoader(MarcaService service) {
        this.service = service;
    }

    @Override
    public void run(String... args) throws Exception {
        List<Marca> marcaList = new ArrayList<>();
        marcaList.add(new Marca("Adidas"));
        marcaList.add(new Marca("Samsung"));
        marcaList.add(new Marca("LG"));
        marcaList.add(new Marca("Gucci"));
        marcaList.add(new Marca("Brastemp"));

        service.saveAll(marcaList);
        System.out.println("Runners executado com sucesso!");
    }
}
