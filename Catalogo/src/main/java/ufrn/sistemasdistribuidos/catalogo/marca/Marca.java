package ufrn.sistemasdistribuidos.catalogo.marca;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Marca {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long cod;
    private String nome;

    public Marca(Long cod, String nome) {
        this.cod = cod;
        this.nome = nome;
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
}
