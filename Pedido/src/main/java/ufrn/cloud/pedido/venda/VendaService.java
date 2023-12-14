package ufrn.cloud.pedido.venda;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class VendaService {
    private final VendaRepository repository;

    public VendaService(VendaRepository repository) {
        this.repository = repository;
    }

    public Venda save(Venda venda) {
        venda.setDataCriacao(LocalDateTime.now());
        return repository.save(venda);
    }

    public Venda update(Venda venda) {
        venda.setDataModificacao(LocalDateTime.now());
        return repository.save(venda);
    }

    public Venda getById(Long id) {
        Optional<Venda> vendaOptional = repository.findById(id);
        if (vendaOptional.isEmpty()) {
            throw new RuntimeException("O de número "+id+" pedido não foi encontrado!");
        } else {
            return vendaOptional.get();
        }
    }
}
