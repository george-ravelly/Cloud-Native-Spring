package ufrn.cloud.pedido.dto;

import java.io.Serializable;
import java.util.Objects;

public class EnderecoDTO implements Serializable {
    private final String rua;
    private final String cidade;

    public EnderecoDTO(String rua, String cidade) {
        this.rua = rua;
        this.cidade = cidade;
    }

    public String getRua() {
        return rua;
    }

    public String getCidade() {
        return cidade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EnderecoDTO entity = (EnderecoDTO) o;
        return Objects.equals(this.rua, entity.rua) &&
                Objects.equals(this.cidade, entity.cidade);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rua, cidade);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "rua = " + rua + ", " +
                "cidade = " + cidade + ")";
    }
}