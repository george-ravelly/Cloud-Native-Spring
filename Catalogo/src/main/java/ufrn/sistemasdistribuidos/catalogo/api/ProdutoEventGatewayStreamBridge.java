package ufrn.sistemasdistribuidos.catalogo.api;

import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;
import ufrn.sistemasdistribuidos.catalogo.enums.ApiProperties;
import ufrn.sistemasdistribuidos.catalogo.produto.service.ProdutoEventGateway;

@Component
public class ProdutoEventGatewayStreamBridge implements ProdutoEventGateway {
    private final StreamBridge streamBridge;

    public ProdutoEventGatewayStreamBridge(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    @Override
    public void sendProdutoCodeEvent(String code) {
        streamBridge.send(
                "criar.estoque.produto.entrada",
                code
        );
        System.out.println("enviando message to " + ApiProperties.SEND_ESTOQUE_NOVO_PRODUTO.getBinding());
    }
}
