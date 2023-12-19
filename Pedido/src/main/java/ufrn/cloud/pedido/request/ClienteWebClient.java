package ufrn.cloud.pedido.request;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ufrn.cloud.pedido.dto.ClienteDTO;

import java.util.Optional;

@FeignClient("usuario-service")
public interface ClienteWebClient {
    @LoadBalanced
    @CircuitBreaker(name = "usuarioService")
    @Retry(name = "retryUsuarioService")
    @Bulkhead(name = "bulkHeadUsuarioService", type = Bulkhead.Type.SEMAPHORE)
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/cliente/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    Optional<ClienteDTO> getClienteById(@PathVariable("id") Long clienteId);
    @LoadBalanced
    @CircuitBreaker(name = "usuarioService")
    @Retry(name = "retryUsuarioService")
    @Bulkhead(name = "bulkHeadUsuarioService", type = Bulkhead.Type.SEMAPHORE)
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/cliente/existe/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    boolean clienteExiste(@PathVariable("id") Long clienteId);
}
