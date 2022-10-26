package com.example.hotel.Model;

public class ProdutoQuarto {
    private int idQuarto;
    private int idProduto;
    private int quantidade;

    public ProdutoQuarto(){

    }

    public ProdutoQuarto(int idQuarto, int idProduto, int quantidade) {
        this.idQuarto = idQuarto;
        this.idProduto = idProduto;
        this.quantidade = quantidade;
    }

    public void setIdQuarto(int idQuarto) {
        this.idQuarto = idQuarto;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public int getIdQuarto() {
        return idQuarto;
    }

    public int getIdProduto() {
        return idProduto;
    }

    public int getQuantidade() {
        return quantidade;
    }
}
