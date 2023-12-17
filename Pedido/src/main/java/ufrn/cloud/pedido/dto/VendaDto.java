package ufrn.cloud.pedido.dto;

import ufrn.cloud.pedido.enums.Status;
import ufrn.cloud.pedido.itemVenda.ItemVenda;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for {@link ufrn.cloud.pedido.venda.Venda}
 */
public record VendaDto(
        Long id,
        ClienteDTO cliente,
        LocalDateTime dataCriacao,
        LocalDateTime dataModificacao,
        double valorTotal,
        Status status,
        List<ItemVenda> itens
) implements Serializable {
}