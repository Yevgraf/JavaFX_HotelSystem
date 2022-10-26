package com.example.hotel.Model;

public class Stock {
    private int idProduto;
    private int quantidade;

    public Stock(){

    }

    public Stock(int idProduto, int quantidade) {
        this.idProduto = idProduto;
        this.quantidade = quantidade;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public int getIdProduto() {
        return idProduto;
    }

    public int getQuantidade() {
        return quantidade;
    }
}
