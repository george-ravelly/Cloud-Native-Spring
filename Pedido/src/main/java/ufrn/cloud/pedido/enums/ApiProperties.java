package ufrn.cloud.pedido.enums;

public enum ApiProperties {
    SEND_VALIDAR_CLIENTE("cliente.valido.entrada"),
    SEND_VALIDAR_ATUALIZAR_ESTOQUE_PRODUTO("validar.atualizar.estoque.venda.entrada");

    private String binding;

    ApiProperties(String detalhes) {
        this.binding = detalhes;
    }

    public String getBinding() {
        return binding;
    }
}
