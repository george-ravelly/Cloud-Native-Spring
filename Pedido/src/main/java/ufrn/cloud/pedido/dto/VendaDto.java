package ufrn.cloud.pedido.dto;

import ufrn.cloud.pedido.enums.Status;
import ufrn.cloud.pedido.itemVenda.ItemVenda;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public record VendaDto(
        Long id,
        ClienteDTO cliente,
        LocalDateTime dataCriacao,
        LocalDateTime dataModificacao,
        double valorTotal,
        Status status,
        List<ItemVenda> itensVenda
) implements Serializable {
}