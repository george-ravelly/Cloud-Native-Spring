package ufrn.cloud.estoque.api;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import ufrn.cloud.estoque.dto.ItemDTO;
import ufrn.cloud.estoque.dto.ItensVendaDTO;
import ufrn.cloud.estoque.estoque.Estoque;
import ufrn.cloud.estoque.estoque.EstoqueRepository;
import ufrn.cloud.estoque.exceptions.NotFoundException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Component
public class EstoqueVendaFunction {
    private final EstoqueRepository estoqueRepository;

    public EstoqueVendaFunction(EstoqueRepository estoqueRepository) {
        this.estoqueRepository = estoqueRepository;
    }

    @Bean
    public Function<ItensVendaDTO, String> validarEAtualizarEstoqueVenda() {
        List<Estoque> estoques = new ArrayList<>();
        return itensVendaDTO -> {
            for (ItemDTO i : itensVendaDTO.itens()) {
                Estoque estoque = estoqueRepository.findByCodigoProduto(i.codigoProduto())
                        .orElseThrow(NotFoundException::new);
                if (estoque.getQuantidade() < i.quantidade()) return itensVendaDTO.codigoVenda().toString();
                if (estoque.getQuantidade() - i.quantidade() < 0) return itensVendaDTO.codigoVenda().toString();
                estoque.setQuantidade(estoque.getQuantidade() - i.quantidade());
                estoque.setDataModificacao(LocalDateTime.now());
                estoques.add(estoque);
            }
            estoqueRepository.saveAll(estoques);
            System.out.println("Atualizando estoque de produtos");
            return "";
        };
    }
}
