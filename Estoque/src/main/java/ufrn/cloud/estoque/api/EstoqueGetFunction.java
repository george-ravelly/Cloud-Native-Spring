package ufrn.cloud.estoque.api;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import ufrn.cloud.estoque.dto.EstoqueDTO;
import ufrn.cloud.estoque.estoque.EstoqueService;

import java.util.function.Function;

@Component
public class EstoqueGetFunction {
    private final EstoqueService service;

    public EstoqueGetFunction(EstoqueService service) {
        this.service = service;
    }

    @Bean
    public Function<String, EstoqueDTO> getProduto() {
        return codigo -> service.getByProdutoCod(Long.parseLong(codigo));
    }
}
