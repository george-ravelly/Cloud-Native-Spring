package ufrn.cloud.pedido.dto;

import ufrn.cloud.pedido.itemVenda.ItemVenda;

import java.util.List;

public record ItensVendaDTO(Long codigoVenda, List<ItemVenda> itens) {
}
