package ufrn.sistemasdistribuidos.catalogo.api;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import ufrn.sistemasdistribuidos.catalogo.dto.MarcaDTO;
import ufrn.sistemasdistribuidos.catalogo.dto.ProdutoDTO;
import ufrn.sistemasdistribuidos.catalogo.produto.Produto;
import ufrn.sistemasdistribuidos.catalogo.produto.ProdutoService;
import ufrn.sistemasdistribuidos.catalogo.produto.service.ProdutoEventGateway;

import java.util.function.Function;

@Component
public class ProdutoPostFunction {

    private final ProdutoService produtoService;
    private final ProdutoEventGateway eventGateway;

    public ProdutoPostFunction(ProdutoService produtoService, ProdutoEventGateway eventGateway) {
        this.produtoService = produtoService;
        this.eventGateway = eventGateway;
    }

    @Bean
    public Function<Produto, ProdutoDTO> criarProduto() {
        return produto -> {
            System.out.println(produto.getNome());
            Produto produtoSalvo = produtoService.save(produto);
            eventGateway.sendProdutoCodeEvent(produtoSalvo.getCod().toString());
            return new ProdutoDTO(
                    produtoSalvo.getCod(),
                    produtoSalvo.getNome(),
                    produtoSalvo.getDescricao(),
                    produtoSalvo.getPreco(),
                    new MarcaDTO(
                            produtoSalvo.getMarca().getCod(),
                            produtoSalvo.getMarca().getNome()
                    )
            );
        };
    }
}
