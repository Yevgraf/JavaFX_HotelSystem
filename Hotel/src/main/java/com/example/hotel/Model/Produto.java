package com.example.hotel.Model;

public class Produto {
    private int idProduto;
    private  String nomeProduto;
    private int idFornecedor;

    public Produto(){

    }

    public Produto(int idProduto, String nomeProduto, int idFornecedor) {
        this.idProduto = idProduto;
        this.nomeProduto = nomeProduto;
        this.idFornecedor = idFornecedor;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public void setIdFornecedor(int idFornecedor) {
        this.idFornecedor = idFornecedor;
    }

    public int getIdProduto() {
        return idProduto;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public int getIdFornecedor() {
        return idFornecedor;
    }
}
