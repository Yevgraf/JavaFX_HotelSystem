package com.example.hotel.Model;

public class TipoColaborador {
    private int idColaborador;
    private String colaborador;

    public TipoColaborador(){

    }

    public TipoColaborador(int idColaborador, String colaborador) {
        this.idColaborador = idColaborador;
        this.colaborador = colaborador;
    }

    public void setIdColaborador(int idColaborador) {
        this.idColaborador = idColaborador;
    }

    public void setColaborador(String colaborador) {
        this.colaborador = colaborador;
    }

    public int getIdColaborador() {
        return idColaborador;
    }

    public String getColaborador() {
        return colaborador;
    }
}
