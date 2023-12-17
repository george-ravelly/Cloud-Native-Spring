package ufrn.cloud.pedido.venda;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import ufrn.cloud.pedido.enums.Status;
import ufrn.cloud.pedido.itemVenda.ItemVenda;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Venda {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long userId;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataModificacao;
    private double valorTotal;
    private Status status;
    @OneToMany(mappedBy = "venda", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<ItemVenda> itensVenda;

    public Venda(Long id, Long userId, LocalDateTime dataCriacao, LocalDateTime dataModificacao, double valorTotal, Status status, List<ItemVenda> itensVenda) {
        this.id = id;
        this.userId = userId;
        this.dataCriacao = dataCriacao;
        this.dataModificacao = dataModificacao;
        this.valorTotal = valorTotal;
        this.status = status;
        this.itensVenda = itensVenda;
    }

    public Venda() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<ItemVenda> getItensVenda() {
        return itensVenda;
    }

    public void setItensVenda(List<ItemVenda> itensVenda) {
        this.itensVenda = itensVenda;
    }
}
