package ufrn.cloud.pedido.venda;

import org.springframework.stereotype.Service;
import ufrn.cloud.pedido.dto.ClienteDTO;
import ufrn.cloud.pedido.dto.EstoqueDTO;
import ufrn.cloud.pedido.dto.VendaDto;
import ufrn.cloud.pedido.enums.Status;
import ufrn.cloud.pedido.exceptions.BadRequestException;
import ufrn.cloud.pedido.exceptions.NotFoundException;
import ufrn.cloud.pedido.request.ClienteWebClient;
import ufrn.cloud.pedido.request.EstoqueWebClient;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class VendaService {
    private final VendaRepository repository;
    private final EstoqueWebClient estoqueWebClient;
    private final ClienteWebClient clienteWebClient;

    public VendaService(VendaRepository repository, EstoqueWebClient estoqueWebClient, ClienteWebClient clienteWebClient) {
        this.repository = repository;
        this.estoqueWebClient = estoqueWebClient;
        this.clienteWebClient = clienteWebClient;
    }

    public VendaDto save(Venda venda) {
        venda.setDataCriacao(LocalDateTime.now());
        venda.setStatus(Status.PENDENTE);

        ClienteDTO clienteDTO = validarCliente(venda.getUserId());

        venda.getItensVenda().forEach(item -> {
            Optional<EstoqueDTO> produto = estoqueWebClient.getByProdutoCodEstoque(item.getCodigoProduto());
            if (produto.isEmpty()) {
                throw new NotFoundException("Produto "+item.getCodigoProduto()+" não encontrado!");
            }
            if (produto.get().quantidade() < item.getQuantidade()) {
                throw new BadRequestException("Produto "+item.getCodigoProduto()+" não possui estoque suficiente!");
            }
            if (produto.get().produtoDTO().preco() * item.getQuantidade() != item.getValorParcial()) {
                throw new BadRequestException("Erro ao processar valor parcial do produto "+ item.getCodigoProduto());
            }
            venda.setValorTotal(venda.getValorTotal() + item.getValorParcial());
        });
        Venda vendaSalva = repository.save(venda);
        return new VendaDto(
                vendaSalva.getId(),
                clienteDTO,
                vendaSalva.getDataCriacao(),
                vendaSalva.getDataModificacao(),
                vendaSalva.getValorTotal(),
                vendaSalva.getStatus(),
                vendaSalva.getItensVenda()
        );
    }

    private ClienteDTO validarCliente(Long userId) {
        if(!clienteWebClient.clienteExiste(userId)) {
            throw new BadRequestException("Usuário invalido!");
        }
        Optional<ClienteDTO> clienteDTO = clienteWebClient.getClienteById(userId);
        if (clienteDTO.isEmpty()) {
            throw new BadRequestException("Erro ao buscar informações do usuário de id "+ userId);
        }
        return clienteDTO.get();
    }

    public Venda update(Venda venda) {
        if (venda.getId() == null) {
            throw new BadRequestException("Número do pedido não informado!");
        }
        if (venda.getStatus() == Status.PROCESSANDO) {
            venda.getItensVenda().forEach(item -> {
                try {
                     estoqueWebClient.updateQuantidadeByCod(
                            item.getCodigoProduto(),
                            item.getQuantidade()
                    );
                } catch (BadRequestException e) {
                    System.out.println(e.getMessage());
                }
            });
        }
        venda.setDataModificacao(LocalDateTime.now());
        return repository.save(venda);
    }

    public VendaDto getById(Long id) {
        Optional<Venda> vendaOptional = repository.findById(id);
        if (vendaOptional.isEmpty()) {
            throw new NotFoundException("O pedido de número "+id+" não foi encontrado!");
        } else {
            ClienteDTO clienteDTO = validarCliente(vendaOptional.get().getUserId());

            return new VendaDto(
                    vendaOptional.get().getId(),
                    clienteDTO,
                    vendaOptional.get().getDataCriacao(),
                    vendaOptional.get().getDataModificacao(),
                    vendaOptional.get().getValorTotal(),
                    vendaOptional.get().getStatus(),
                    vendaOptional.get().getItensVenda()
            );
        }
    }
}
