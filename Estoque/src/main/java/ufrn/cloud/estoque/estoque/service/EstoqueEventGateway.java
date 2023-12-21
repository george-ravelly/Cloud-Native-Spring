package ufrn.cloud.estoque.estoque.service;

import ufrn.cloud.estoque.dto.EstoqueDTO;

public interface EstoqueEventGateway {

    void sendProdutoCodeEvent(String code);
    void sendEstoqueProdutoEvent(EstoqueDTO estoqueDTO);

}
