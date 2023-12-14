package ufrn.sistemasdistribuidos.catalogo.produto;

import jakarta.persistence.*;
import ufrn.sistemasdistribuidos.catalogo.marca.Marca;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @SequenceGenerator(name = "nome_id", sequenceName = "prod_seq_id", allocationSize = 1)
    private Long cod;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataModificacao;
    private String nome;
    private double preco;
    private String descricao;
    @OneToOne
    private Marca marca;
    private Categoria categoria;
    public Produto(Long cod, String nome, double preco, String descricao, Marca marca, Categoria categoria) {
        this.cod = cod;
        this.nome = nome;
        this.preco = preco;
        this.descricao = descricao;
        this.marca = marca;
        this.categoria = categoria;
    }

    public Produto(Long cod, LocalDateTime dataCriacao, LocalDateTime dataModificacao, String nome, double preco, String descricao, Marca marca, Categoria categoria) {
        this.cod = cod;
        this.dataCriacao = dataCriacao;
        this.dataModificacao = dataModificacao;
        this.nome = nome;
        this.preco = preco;
        this.descricao = descricao;
        this.marca = marca;
        this.categoria = categoria;
    }

    public Produto() {

    }

    public Long getCod() {
        return cod;
    }

    public void setCod(Long cod) {
        this.cod = cod;
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

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
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