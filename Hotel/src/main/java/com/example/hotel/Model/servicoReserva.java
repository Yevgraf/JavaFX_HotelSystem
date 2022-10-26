package com.example.hotel.Model;

public class servicoReserva {
    private int idReserva;
    private int idServico;

    public servicoReserva(){

    }
    public servicoReserva(int idReserva, int idServico) {
        this.idReserva = idReserva;
        this.idServico = idServico;
    }

    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }

    public void setIdServico(int idServico) {
        this.idServico = idServico;
    }

    public int getIdReserva() {
        return idReserva;
    }

    public int getIdServico() {
        return idServico;
    }
}
