package ufrn.cloud.pedido.request;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ufrn.cloud.pedido.dto.EstoqueDTO;
import ufrn.cloud.pedido.dto.ProdutoDTO;

import java.util.Optional;

@FeignClient("estoque")
public interface EstoqueWebClient {
    @LoadBalanced
    @CircuitBreaker(name = "estoque")
    @Retry(name = "retryEstoque")
    @Bulkhead(name = "bulkHeadEstoque", type = Bulkhead.Type.SEMAPHORE)
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/produto/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    Optional<EstoqueDTO> getByProdutoCodEstoque(@PathVariable("id") Long codigoProduto);

    @LoadBalanced
    @CircuitBreaker(name = "estoque")
    @Bulkhead(name = "bulkHeadEstoque", type = Bulkhead.Type.SEMAPHORE)
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
