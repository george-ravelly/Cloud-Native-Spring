package ufrn.cloud.pedido.venda;

import org.springframework.stereotype.Service;
import ufrn.cloud.pedido.request.CatalogoWebClient;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@Service
public class VendaService {
    private final VendaRepository repository;
    private final CatalogoWebClient webClient;

    public VendaService(VendaRepository repository, CatalogoWebClient webClient) {
        this.repository = repository;
        this.webClient = webClient;
    }

    public Venda save(Venda venda) {
        venda.getItemVendas().forEach(itemVenda -> {
            Map<String, Object> produtoEstoque = webClient.getData(itemVenda.getCodigoProduto());
            if (Integer.getInteger((String) produtoEstoque.get("quantidade")) < itemVenda.getQuantidade()) {
                throw new RuntimeException("Produto fora de estoque!");
            }
        });
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
