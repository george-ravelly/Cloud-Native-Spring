package ufrn.cloud.pedido.itemVenda;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import ufrn.cloud.pedido.venda.Venda;

import java.time.LocalDateTime;

@Entity
public class ItemVenda {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataModificacao;
    private long quantidade;
    private double valorParcial;
    private long codigoProduto;
    @ManyToOne
    @JoinColumn(name = "venda_id")
    @JsonBackReference
    private Venda venda;

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    public ItemVenda(Long id, LocalDateTime dataCriacao, LocalDateTime dataModificacao, long quantidade, double valorParcial, long codigoProduto) {
        this.id = id;
        this.dataCriacao = dataCriacao;
        this.dataModificacao = dataModificacao;
        this.quantidade = quantidade;
        this.valorParcial = valorParcial;
        this.codigoProduto = codigoProduto;
    }

    public ItemVenda() {
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

    public long getCodigoProduto() {
        return codigoProduto;
    }

    public void setCodigoProduto(long codigoProduto) {
        this.codigoProduto = codigoProduto;
    }

    public double getValorParcial() {
        return valorParcial;
    }

    public void setValorParcial(double valorParcial) {
        this.valorParcial = valorParcial;
    }
}
