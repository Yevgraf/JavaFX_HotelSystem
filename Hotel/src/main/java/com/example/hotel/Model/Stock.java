package com.example.hotel.Model;

public class Stock {
    private int idStock;
    private int idProduto;
    private int quantidade;

    public Stock(){

    }

    public Stock(int idStock, int idProduto, int quantidade) {
        this.idStock = idStock;
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

    public void setIdStock(int idStock) {
        this.idStock = idStock;
    }

    public int getIdStock() {
        return idStock;
    }
}
