package Model;

import java.util.Date;

public class EntradaStock {
    private int idEntradaStock;
    private int idProduto;
    private int idFornecedor;
    private int idColaborador;
    private Date dataEntrega;
    private int quantidade;
    private double preco;

    public EntradaStock(){

    }


    public EntradaStock(int idEntradaStock, int idProduto, int idFornecedor, int idColaborador, Date dataEntrega, int quantidade, double preco) {
        this.idEntradaStock = idEntradaStock;
        this.idProduto = idProduto;
        this.idFornecedor = idFornecedor;
        this.idColaborador = idColaborador;
        this.dataEntrega = dataEntrega;
        this.quantidade = quantidade;
        this.preco = preco;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    public void setIdFornecedor(int idFornecedor) {
        this.idFornecedor = idFornecedor;
    }

    public void setIdColaborador(int idColaborador) {
        this.idColaborador = idColaborador;
    }

    public void setDataEntrega(Date dataEntrega) {
        this.dataEntrega = dataEntrega;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public int getIdProduto() {
        return idProduto;
    }

    public int getIdFornecedor() {
        return idFornecedor;
    }

    public int getIdColaborador() {
        return idColaborador;
    }

    public Date getDataEntrega() {
        return dataEntrega;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public double getPreco() {
        return preco;
    }

    public void setIdEntradaStock(int idEntradaStock) {
        this.idEntradaStock = idEntradaStock;
    }

    public int getIdEntradaStock() {
        return idEntradaStock;
    }
}
