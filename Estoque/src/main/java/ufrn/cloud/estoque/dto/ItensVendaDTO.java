package ufrn.cloud.estoque.dto;

import java.util.List;

public record ItensVendaDTO(Long codigoVenda, List<ItemDTO> itens) {
}
