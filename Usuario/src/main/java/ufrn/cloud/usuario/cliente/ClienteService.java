package ufrn.cloud.usuario.cliente;

import org.springframework.stereotype.Service;
import ufrn.cloud.usuario.exceptions.BadRequestException;

@Service
public class ClienteService {
    private final ClienteRepository repository;

    public ClienteService(ClienteRepository repository) {
        this.repository = repository;
    }

    public Cliente save(Cliente cliente) {
        cliente.getEndereco().setCliente(cliente);
        return repository.save(cliente);
    }

    public Cliente update(Cliente cliente) {
        return repository.save(cliente);
    }

    public Cliente getById(Long id) {
        var resultado = repository.findById(id);
        if (resultado.isPresent()) return resultado.get();
        throw new BadRequestException("Cliente não encontrado!");
    }

    public boolean clienteExiste(Long id) {
        return repository.existsById(id);
    }
}
