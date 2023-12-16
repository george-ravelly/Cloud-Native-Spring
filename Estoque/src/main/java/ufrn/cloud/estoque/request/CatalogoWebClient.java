package ufrn.cloud.estoque.request;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ufrn.cloud.estoque.dto.ProdutoDTO;

import java.util.Optional;

@FeignClient("catalogo")
public interface CatalogoWebClient {
    @LoadBalanced
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/catalogo/estoque/produto/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    Optional<ProdutoDTO> getProdutoByCod(@PathVariable("id") Long codigoProduto);
}
