package ufrn.cloud.pedido.venda;

import feign.FeignException;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.transaction.Transactional;
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
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class VendaService {
    private final VendaRepository repository;
    private final EstoqueWebClient estoqueWebClient;
    private final ClienteWebClient clienteWebClient;

    private final Logger logger = Logger.getLogger(VendaService.class.getName());
    private final Map<Long, ClienteDTO> CLIENTECACHE = new HashMap<>();
    private final Map<Long, VendaDto> VENDASCACHE = new HashMap<>();

    public VendaService(VendaRepository repository, EstoqueWebClient estoqueWebClient, ClienteWebClient clienteWebClient) {
        this.repository = repository;
        this.estoqueWebClient = estoqueWebClient;
        this.clienteWebClient = clienteWebClient;
    }

//    @CircuitBreaker(name = "estoque")
////    @Retry(name = "retryEstoque", fallbackMethod = "fallbackEstoque")
//    @Bulkhead(name = "bulkHeadEstoque", type = Bulkhead.Type.SEMAPHORE)
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

    @CircuitBreaker(name = "usuarioService", fallbackMethod = "fallbackUsuarioService")
    @Retry(name = "retryUsuarioService", fallbackMethod = "fallbackUsuarioService")
    @Bulkhead(name = "bulkHeadUsuarioService", type = Bulkhead.Type.SEMAPHORE, fallbackMethod = "fallbackUsuarioService")
    private ClienteDTO validarCliente(Long userId) {
        try {
            if(!clienteWebClient.clienteExiste(userId)) {
                throw new BadRequestException("Usuário invalido!");
            }
            Optional<ClienteDTO> clienteDTO = clienteWebClient.getClienteById(userId);
            if (clienteDTO.isEmpty()) {
                throw new BadRequestException("Erro ao buscar informações do usuário de id "+ userId);
            }
            CLIENTECACHE.put(userId, clienteDTO.get());
            return clienteDTO.get();
        } catch (FeignException.BadRequest badRequest) {
            throw new BadRequestException(badRequest.getMessage());
        }
    }

    public ClienteDTO fallbackUsuarioService(Long userId, Throwable t) {
        ClienteDTO clienteDTO = CLIENTECACHE.getOrDefault(userId, null);
        if (clienteDTO != null) {
            logger.log(Level.INFO, "Adicionando cliente do cache local");
            return clienteDTO;
        }
        else throw new NotFoundException("Erro ao buscar informações do usuário");
    }

//    @CircuitBreaker(name = "estoque")
//    @Bulkhead(name = "bulkHeadEstoque", type = Bulkhead.Type.SEMAPHORE)
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
                } catch (FeignException.BadRequest e) {
                   throw new BadRequestException(e.getMessage());
                }
            });
        }
        venda.setDataModificacao(LocalDateTime.now());
        return repository.save(venda);
    }

    @CircuitBreaker(name = "estoque", fallbackMethod = "fallbackVenda")
    @Retry(name = "retryEstoque", fallbackMethod = "fallbackVenda")
    @Bulkhead(name = "bulkHeadEstoque", type = Bulkhead.Type.SEMAPHORE, fallbackMethod = "fallbackVenda")
    @Transactional
    public VendaDto getById(Long id) {
        Optional<Venda> vendaOptional = repository.findById(id);
        if (vendaOptional.isEmpty()) {
            throw new NotFoundException("O pedido de número "+id+" não foi encontrado!");
        } else {
            ClienteDTO clienteDTO = validarCliente(vendaOptional.get().getUserId());

            VendaDto vendaDto = new VendaDto(
                    vendaOptional.get().getId(),
                    clienteDTO,
                    vendaOptional.get().getDataCriacao(),
                    vendaOptional.get().getDataModificacao(),
                    vendaOptional.get().getValorTotal(),
                    vendaOptional.get().getStatus(),
                    vendaOptional.get().getItensVenda()
            );
            logger.log(Level.INFO, "Adicionando pedido ao cache local");
            VENDASCACHE.put(id, vendaDto);
            return vendaDto;
        }
    }

    public VendaDto fallbackVenda(Long id, Throwable t) {
        VendaDto vendaDto = VENDASCACHE.getOrDefault(id, null);
        if (vendaDto != null) {
            logger.log(Level.INFO, "Pegando pedido do cache");
            return vendaDto;
        }
        throw new NotFoundException("Pedido de número "+ id+ " não foi encontrado!");
    }
}
