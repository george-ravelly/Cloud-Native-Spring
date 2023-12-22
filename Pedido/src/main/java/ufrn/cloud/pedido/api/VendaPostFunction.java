package ufrn.cloud.pedido.api;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import ufrn.cloud.pedido.dto.EstoqueDTO;
import ufrn.cloud.pedido.dto.ItensVendaDTO;
import ufrn.cloud.pedido.dto.VendaClienteDTO;
import ufrn.cloud.pedido.enums.Status;
import ufrn.cloud.pedido.exceptions.NotFoundException;
import ufrn.cloud.pedido.service.PedidoEventGateway;
import ufrn.cloud.pedido.venda.Venda;
import ufrn.cloud.pedido.venda.VendaRepository;

import java.time.LocalDateTime;
import java.util.function.Function;

@Component
public class VendaPostFunction {
    private final VendaRepository vendaRepository;
    private final PedidoEventGateway eventGateway;

    public VendaPostFunction(VendaRepository vendaRepository, PedidoEventGateway eventGateway) {
        this.vendaRepository = vendaRepository;
        this.eventGateway = eventGateway;
    }

    @Bean
    public Function<Venda, Venda> cadastrarVenda() {
        return venda -> {
            venda.setDataCriacao(LocalDateTime.now());
            venda.setStatus(Status.PENDENTE);

            venda.getItensVenda().forEach(item -> {
                venda.setValorTotal(venda.getValorTotal() + item.getValorParcial());
            });

            Venda venda1 = vendaRepository.save(venda);
            eventGateway.sendClienteIdToValidationForVenda(new VendaClienteDTO(
                    venda.getUserId(),
                    venda.getId()
            ));
            return venda1;
        };
    }

    @Bean
    public Function<Venda, Venda> updateVenda() {
        return venda -> {
            venda.setDataModificacao(LocalDateTime.now());
            if (venda.getStatus() == Status.PROCESSANDO) {
                eventGateway.sendVendaToValidationOfEstoqueProdutcs(new ItensVendaDTO(
                        venda.getId(),
                        venda.getItensVenda()
                ));
            }
            return vendaRepository.save(venda);
        };
    }

    @Bean
    public Function<String, String> cancelarVenda() {
        return vendaId -> {
            if (vendaId.isEmpty()) return "";
            Venda venda = vendaRepository.findById(Long.parseLong(vendaId)).orElseThrow(NotFoundException::new);
            vendaRepository.delete(venda);
            venda.setId(null);
            vendaRepository.save(venda);
            System.out.println("Venda cancelada: " + venda.getId());
            return "Venda cancelada!";
        };
    }
}
