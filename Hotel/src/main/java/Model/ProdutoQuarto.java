package Model;

public class ProdutoQuarto {
    private int idQuarto;
    private int idServico;

    private int quantidade;

    public ProdutoQuarto(){

    }

    public ProdutoQuarto(int idQuarto, int idServico, int quantidade) {
        this.idQuarto = idQuarto;
        this.idServico = idServico;
        this.quantidade = quantidade;
    }

    public void setIdQuarto(int idQuarto) {
        this.idQuarto = idQuarto;
    }


    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public int getIdQuarto() {
        return idQuarto;
    }

    public void setIdServico(int idServico) {
        this.idServico = idServico;
    }

    public int getIdServico() {
        return idServico;
    }

    public int getQuantidade() {
        return quantidade;
    }
}
