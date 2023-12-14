package ufrn.cloud.pedido.request;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@FeignClient("catalogo")
public interface CatalogoWebClient {
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/catalogo/estoque/produto/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    Map<String, Object> getData(@PathVariable("id") Long codigoProduto);
}
