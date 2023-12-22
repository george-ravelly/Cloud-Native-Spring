package ufrn.cloud.pedido.service;

import ufrn.cloud.pedido.dto.ItensVendaDTO;
import ufrn.cloud.pedido.dto.VendaClienteDTO;
import ufrn.cloud.pedido.venda.Venda;

public interface PedidoEventGateway {
    void sendClienteId(String id);
    void sendVendaToValidationOfEstoqueProdutcs(ItensVendaDTO itensVendaDTO);

    void sendClienteIdToValidationForVenda(VendaClienteDTO vendaClienteDTO);

}
