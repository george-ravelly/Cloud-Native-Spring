package ufrn.cloud.pedido.dto;

import ufrn.cloud.pedido.enums.Status;

import java.time.LocalDateTime;
import java.util.List;

public class VendaMessageDTO {
    private long id;
    private ClienteDTO cliente;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataModificacao;
    private double valorTotal;
    private Status status;
    private List<ItemDTO> itensVenda;

    public VendaMessageDTO(long id, ClienteDTO cliente, LocalDateTime dataCriacao, LocalDateTime dataModificacao, double valorTotal, Status status, List<ItemDTO> itensVenda) {
        this.id = id;
        this.cliente = cliente;
        this.dataCriacao = dataCriacao;
        this.dataModificacao = dataModificacao;
        this.valorTotal = valorTotal;
        this.status = status;
        this.itensVenda = itensVenda;
    }

    @Override
    public String toString() {
        return "VendaMessageDTO{" +
                "id=" + id +
                ", cliente=" + cliente +
                ", dataCriacao=" + dataCriacao +
                ", dataModificacao=" + dataModificacao +
                ", valorTotal=" + valorTotal +
                ", status=" + status +
                ", itensVenda=" + itensVenda +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ClienteDTO getCliente() {
        return cliente;
    }

    public void setCliente(ClienteDTO cliente) {
        this.cliente = cliente;
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

    public List<ItemDTO> getItensVenda() {
        return itensVenda;
    }

    public void setItensVenda(List<ItemDTO> itensVenda) {
        this.itensVenda = itensVenda;
    }
}
