package ufrn.cloud.pedido.api;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import ufrn.cloud.pedido.dto.ItemDTO;
import ufrn.cloud.pedido.dto.VendaDto;
import ufrn.cloud.pedido.dto.VendaMessageDTO;
import ufrn.cloud.pedido.exceptions.NotFoundException;
import ufrn.cloud.pedido.venda.Venda;
import ufrn.cloud.pedido.venda.VendaService;

import java.util.function.Function;

@Component
public class VendaGetFunction {
    private final VendaService vendaService;

    public VendaGetFunction(VendaService vendaService) {
        this.vendaService = vendaService;
    }

    @Bean
    public Function<String, VendaMessageDTO> venda() {
        return codigo -> {
            VendaDto venda = vendaService.getById(Long.parseLong(codigo));
            return new VendaMessageDTO(
                    venda.id(),
                    venda.cliente(),
                    venda.dataCriacao(),
                    venda.dataModificacao(),
                    venda.valorTotal(),
                    venda.status(),
                    null
            );
        };
    }
}
