package Model;

public class ProdutoQuarto {
    private int id;

    private int idQuarto;
    private int idProduto;
    private int quantidade;

    public ProdutoQuarto(int idQuarto, int idProduto, int quantidade) {
        this.idQuarto = idQuarto;
        this.idProduto = idProduto;
        this.quantidade = quantidade;
    }

    public ProdutoQuarto(int id, int idQuarto, int idProduto, int quantidade) {
        this.id = id;
        this.idQuarto = idQuarto;
        this.idProduto = idProduto;
        this.quantidade = quantidade;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    public int getIdQuarto() {
        return idQuarto;
    }

    public void setIdQuarto(int idQuarto) {
        this.idQuarto = idQuarto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}
