package ufrn.sistemasdistribuidos.catalogo.produto;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "produto")
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @SequenceGenerator(name = "nome_id", sequenceName = "prod_seq_id", allocationSize = 1)
    private Long id;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataModificacao;
    private String nome;
    private double preco;
    private String descricao;
    private Categoria categoria;
    public Produto(Long id, String nome, double preco, String descricao, Categoria categoria) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.descricao = descricao;
        this.categoria = categoria;
    }

    public Produto(Long id, LocalDateTime dataCriacao, LocalDateTime dataModificacao, String nome, double preco, String descricao, Categoria categoria) {
        this.id = id;
        this.dataCriacao = dataCriacao;
        this.dataModificacao = dataModificacao;
        this.nome = nome;
        this.preco = preco;
        this.descricao = descricao;
        this.categoria = categoria;
    }

    public Produto() {
        // Construtor padrão
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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public double calcularPrecoComDesconto(double descontoPercentual) {
        if (descontoPercentual < 0 || descontoPercentual > 100) {
            throw new IllegalArgumentException("O desconto deve estar entre 0 e 100%");
        }
        double desconto = (descontoPercentual / 100) * preco;
        return preco - desconto;
    }

    @Override
    public String toString() {
        return "Produto{" +
                "nome='" + nome + '\'' +
                ", preco=" + preco +
                ", descricao='" + descricao + '\'' +
                ", categoria=" + categoria +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Produto produto = (Produto) o;
        return Double.compare(produto.preco, preco) == 0 &&
                Objects.equals(nome, produto.nome) &&
                Objects.equals(descricao, produto.descricao) &&
                categoria == produto.categoria;
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, preco, descricao, categoria);
    }
}