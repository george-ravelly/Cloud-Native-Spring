package ufrn.cloud.estoque.dto;

import java.time.LocalDateTime;

public record EstoqueDTO(LocalDateTime data, long quantidade, ProdutoDTO produtoDTO) {
}
