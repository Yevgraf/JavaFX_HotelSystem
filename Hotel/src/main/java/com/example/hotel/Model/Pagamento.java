package com.example.hotel.Model;

public class Pagamento {
    private int idPagamento;
    private String metodoPagamento;

    public pagamento(){

    }

    public Pagamento(int idPagamento, String metodoPagamento) {
        this.idPagamento = idPagamento;
        this.metodoPagamento = metodoPagamento;
    }

    public void setIdPagamento(int idPagamento) {
        this.idPagamento = idPagamento;
    }

    public void setMetodoPagamento(String metodoPagamento) {
        this.metodoPagamento = metodoPagamento;
    }

    public int getIdPagamento() {
        return idPagamento;
    }

    public String getMetodoPagamento() {
        return metodoPagamento;
    }
}
