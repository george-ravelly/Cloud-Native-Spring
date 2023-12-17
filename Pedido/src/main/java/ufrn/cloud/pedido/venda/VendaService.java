package ufrn.cloud.pedido.venda;

import org.springframework.stereotype.Service;
import ufrn.cloud.pedido.dto.EstoqueDTO;
import ufrn.cloud.pedido.dto.ProdutoDTO;
import ufrn.cloud.pedido.enums.Status;
import ufrn.cloud.pedido.exceptions.BadRequestException;
import ufrn.cloud.pedido.exceptions.NotFoundException;
import ufrn.cloud.pedido.itemVenda.ItemVenda;
import ufrn.cloud.pedido.request.EstoqueWebClient;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VendaService {
    private final VendaRepository repository;
    private final EstoqueWebClient webClient;

    public VendaService(VendaRepository repository, EstoqueWebClient webClient) {
        this.repository = repository;
        this.webClient = webClient;
    }

    public Venda save(List<ItemVenda> itens) {
        Venda venda = new Venda();
        venda.setDataCriacao(LocalDateTime.now());
        venda.setStatus(Status.PENDENTE);
        venda.setUserId((long) 1);
        itens.forEach(item -> {
            Optional<EstoqueDTO> produto = webClient.getByProdutoCodEstoque(item.getCodigoProduto());
            if (produto.isEmpty()) {
                throw new NotFoundException("Produto "+item.getCodigoProduto()+" não encontrado!");
            }
            if (produto.get().quantidade() < item.getQuantidade()) {
                throw new BadRequestException("Produto "+item.getCodigoProduto()+" não possui estoque suficiente!");
            }
            if (produto.get().produtoDTO().preco() * item.getQuantidade() != item.getValorParcial()) {
                throw new BadRequestException("Erro ao processar valor parcial do produto "+ item.getCodigoProduto());
            }
            item.setVenda(venda);
            venda.setValorTotal(venda.getValorTotal() + item.getValorParcial());
        });
        venda.setItensVenda(itens);
        return repository.save(venda);
    }

    public Venda update(Venda venda) {
        if (venda.getId() == null) {
            throw new BadRequestException("Número do pedido não informado!");
        }
        venda.setDataModificacao(LocalDateTime.now());
        return repository.save(venda);
    }

    public Venda getById(Long id) {
        Optional<Venda> vendaOptional = repository.findById(id);
        if (vendaOptional.isEmpty()) {
            throw new NotFoundException("O pedido de número "+id+" não foi encontrado!");
        } else {
            return vendaOptional.get();
        }
    }
}
