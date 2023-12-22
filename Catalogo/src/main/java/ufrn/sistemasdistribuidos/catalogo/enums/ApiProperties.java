package ufrn.sistemasdistribuidos.catalogo.enums;

public enum ApiProperties {
    SEND_ESTOQUE_NOVO_PRODUTO("criar.estoque.produto.entrada");

    private String binding;

    ApiProperties(String detalhes) {
        this.binding = detalhes;
    }

    public String getBinding() {
        return binding;
    }
}