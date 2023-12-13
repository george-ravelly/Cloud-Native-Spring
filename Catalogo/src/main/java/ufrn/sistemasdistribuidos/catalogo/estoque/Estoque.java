package ufrn.sistemasdistribuidos.catalogo.estoque;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import ufrn.sistemasdistribuidos.catalogo.produto.Produto;

import java.time.LocalDateTime;

public class Estoque {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataModificacao;
    private long quantidade;
    @JoinColumn
    private Produto produto;

    public Estoque(Long id, long quantidade, Produto produto) {
        this.id = id;
        this.quantidade = quantidade;
        this.produto = produto;
    }

    public Estoque() {}
}
