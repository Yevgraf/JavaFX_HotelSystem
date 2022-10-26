package com.example.hotel.Model;

public class tipoQuarto {
    private int idTipoQuarto;
    private String tipo;
    private boolean vista;

    public tipoQuarto(){

    }

    public tipoQuarto(int idTipoQuarto, String tipo, boolean vista) {
        this.idTipoQuarto = idTipoQuarto;
        this.tipo = tipo;
        this.vista = vista;
    }

    public void setIdTipoQuarto(int idTipoQuarto) {
        this.idTipoQuarto = idTipoQuarto;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setVista(boolean vista) {
        this.vista = vista;
    }

    public int getIdTipoQuarto() {
        return idTipoQuarto;
    }

    public String getTipo() {
        return tipo;
    }

    public boolean isVista() {
        return vista;
    }
}
