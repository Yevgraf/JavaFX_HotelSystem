package Model;

public class Stock {

    private String idProduto;

    private Integer unidades;

    public String getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(String idProduto) {
        this.idProduto = idProduto;
    }

    public Integer getUnidades() {
        return unidades;
    }

    public void setUnidades(Integer unidades) {
        this.unidades = unidades;
    }

    public Stock(String idProduto, Integer unidades) {
        this.idProduto = idProduto;
        this.unidades = unidades;
    }

    public Stock() {
    }
}
