package ufrn.cloud.pedido.api;

import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;
import ufrn.cloud.pedido.dto.ItensVendaDTO;
import ufrn.cloud.pedido.dto.VendaClienteDTO;
import ufrn.cloud.pedido.enums.ApiProperties;
import ufrn.cloud.pedido.service.PedidoEventGateway;

@Component
public class VendaEventGatewayStreamBridge implements PedidoEventGateway {
    private final StreamBridge streamBridge;

    public VendaEventGatewayStreamBridge(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    @Override
    public void sendClienteId(String id) {
    }

    @Override
    public void sendVendaToValidationOfEstoqueProdutcs(ItensVendaDTO itensVendaDTO) {
        streamBridge.send(
                ApiProperties.SEND_VALIDAR_ATUALIZAR_ESTOQUE_PRODUTO.getBinding(),
                itensVendaDTO
        );
    }

    @Override
    public void sendClienteIdToValidationForVenda(VendaClienteDTO vendaClienteDTO) {
        streamBridge.send(
                ApiProperties.SEND_VALIDAR_CLIENTE.getBinding(),
                vendaClienteDTO
        );
    }
}
