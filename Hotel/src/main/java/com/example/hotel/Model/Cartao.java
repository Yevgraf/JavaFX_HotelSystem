package com.example.hotel.Model;

public class Cartao {
    private int idCartao;
    private String numCartao;
    private boolean cartaoMestre;

    public void cartao(){

    }

    public Cartao(int idCartao, String numCartao, boolean cartaoMestre) {
        this.idCartao = idCartao;
        this.numCartao = numCartao;
        this.cartaoMestre = cartaoMestre;
    }

    public void setIdCartao(int idCartao) {
        this.idCartao = idCartao;
    }

    public void setNumCartao(String numCartao) {
        this.numCartao = numCartao;
    }

    public void setCartaoMestre(boolean cartaoMestre) {
        this.cartaoMestre = cartaoMestre;
    }

    public int getIdCartao() {
        return idCartao;
    }

    public String getNumCartao() {
        return numCartao;
    }

    public boolean isCartaoMestre() {
        return cartaoMestre;
    }
}
