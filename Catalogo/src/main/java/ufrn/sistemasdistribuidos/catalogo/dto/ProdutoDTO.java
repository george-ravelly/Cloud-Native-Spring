package ufrn.sistemasdistribuidos.catalogo.dto;

public record ProdutoDTO (Long cod, String nome, String descricao, double preco, MarcaDTO marca) {
}
