package ufrn.cloud.pedido.dto;

import java.time.LocalDateTime;

public record ItemDTO(
        long quantidade,
        double valorParcial,
        long codigoProduto
) {
}
