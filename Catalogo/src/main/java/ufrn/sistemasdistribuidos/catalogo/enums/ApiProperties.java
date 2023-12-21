package ufrn.sistemasdistribuidos.catalogo.enums;

public enum ApiProperties {
    SEND_ESTOQUE_NOVO_PRODUTO("criarEstoqueProduto-out-0");

    private String binding;

    ApiProperties(String detalhes) {
        this.binding = detalhes;
    }

    public String getBinding() {
        return binding;
    }
}
