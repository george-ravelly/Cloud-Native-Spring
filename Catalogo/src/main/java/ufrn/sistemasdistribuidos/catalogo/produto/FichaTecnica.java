package ufrn.sistemasdistribuidos.catalogo.produto;

public class FichaTecnica {
    private String modelo;
    private String fabricante;
    private int anoFabricacao;

    // Construtores
    public FichaTecnica(String modelo, String fabricante, int anoFabricacao) {
        this.modelo = modelo;
        this.fabricante = fabricante;
        this.anoFabricacao = anoFabricacao;
    }

    public FichaTecnica() {
        // Construtor padrão
    }

    // Métodos getters e setters

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public int getAnoFabricacao() {
        return anoFabricacao;
    }

    public void setAnoFabricacao(int anoFabricacao) {
        this.anoFabricacao = anoFabricacao;
    }
}
