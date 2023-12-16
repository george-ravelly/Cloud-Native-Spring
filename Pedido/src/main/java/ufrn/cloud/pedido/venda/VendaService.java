package ufrn.cloud.pedido.venda;

import org.springframework.stereotype.Service;
import ufrn.cloud.pedido.dto.EstoqueDTO;
import ufrn.cloud.pedido.request.EstoqueWebClient;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class VendaService {
    private final VendaRepository repository;
    private final EstoqueWebClient webClient;

    public VendaService(VendaRepository repository, EstoqueWebClient webClient) {
        this.repository = repository;
        this.webClient = webClient;
    }

    public Venda save(Venda venda) {
        venda.getItemVendas().forEach(itemVenda -> {
            Optional<EstoqueDTO> produtoEstoque = webClient.getByProdutoCodEstoque(itemVenda.getCodigoProduto());
            if (produtoEstoque.isEmpty()) {
                throw new RuntimeException("Produto "+itemVenda.getCodigoProduto()+" não encontrado!");
            }
            if (produtoEstoque.get().quantidade() < itemVenda.getQuantidade()) {
                throw new RuntimeException("Produto "+itemVenda.getCodigoProduto()+" fora de estoque!");
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
            throw new RuntimeException("O pedido de número "+id+" não foi encontrado!");
        } else {
            return vendaOptional.get();
        }
    }
}
