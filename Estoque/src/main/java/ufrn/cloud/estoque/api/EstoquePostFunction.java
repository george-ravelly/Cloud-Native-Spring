package ufrn.cloud.estoque.api;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import ufrn.cloud.estoque.dto.EstoqueDTO;
import ufrn.cloud.estoque.estoque.Estoque;
import ufrn.cloud.estoque.estoque.EstoqueService;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@Component
public class EstoquePostFunction {
    private final EstoqueService estoqueService;

    public EstoquePostFunction(EstoqueService estoqueService) {
        this.estoqueService = estoqueService;
    }

    @Bean
    public Function<String, Estoque> criarEstoqueProduto() {
        return codigo -> {
            Estoque estoque = new Estoque();
            estoque.setCodigoProduto(Long.parseLong(codigo));
            estoque.setQuantidade(0);

            return estoqueService.save(estoque);
        };
    }

    @Bean
    public Function<Estoque, Estoque> updateEstoqueProduto() {
        return estoqueService::update;
    }
}
