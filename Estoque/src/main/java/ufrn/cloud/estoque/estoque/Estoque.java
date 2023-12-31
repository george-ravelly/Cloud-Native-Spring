package ufrn.cloud.estoque.estoque;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "estoque")
public class Estoque {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataModificacao;
    private long quantidade;
    private long codigoProduto;

    public Estoque(Long id, long quantidade, long codigoProduto) {
        this.id = id;
        this.quantidade = quantidade;
        this.codigoProduto = codigoProduto;
    }

    public Estoque() {}

    public long getCodigoProduto() {
        return codigoProduto;
    }

    public void setCodigoProduto(long codigoProduto) {
        this.codigoProduto = codigoProduto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public LocalDateTime getDataModificacao() {
        return dataModificacao;
    }

    public void setDataModificacao(LocalDateTime dataModificacao) {
        this.dataModificacao = dataModificacao;
    }

    public long getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(long quantidade) {
        this.quantidade = quantidade;
    }
}
