package ufrn.cloud.pedido.request;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ufrn.cloud.pedido.dto.EstoqueDTO;

import java.util.Optional;

@FeignClient("estoque")
public interface EstoqueWebClient {
    @LoadBalanced
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/produto/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    Optional<EstoqueDTO> getByProdutoCodEstoque(@PathVariable("id") Long codigoProduto);

    @LoadBalanced
    @RequestMapping(
            method = RequestMethod.PUT,
            value = "/produto/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    Optional<EstoqueDTO> updateQuantidadeByCod(
            @PathVariable("id") long codigoProduto,
            @RequestBody long quantidade
    );
}
