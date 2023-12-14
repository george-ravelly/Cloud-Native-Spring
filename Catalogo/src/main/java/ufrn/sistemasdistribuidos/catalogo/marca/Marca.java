package ufrn.sistemasdistribuidos.catalogo.marca;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import ufrn.sistemasdistribuidos.catalogo.produto.Produto;

import java.util.List;

@Entity
public class Marca {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long cod;
    private String nome;
    @OneToMany(mappedBy = "marca", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Produto> produtos;
    public Marca(Long cod, String nome, List<Produto> produtos) {
        this.cod = cod;
        this.nome = nome;
        this.produtos = produtos;
    }
    public Marca(String nome) {
        this.nome = nome;
    }

    public Marca() {
    }

    public Long getCod() {
        return cod;
    }

    public void setCod(Long cod) {
        this.cod = cod;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }
}
