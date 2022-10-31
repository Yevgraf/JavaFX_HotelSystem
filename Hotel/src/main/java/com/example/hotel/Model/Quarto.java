package com.example.hotel.Model;

public class Quarto {
    private int idQuarto;
    private int piso;
    private int idTipoQuarto;
    private boolean wifi;
    private boolean cofre;

    public Quarto(){

    }

    public Quarto(int idQuarto, int piso, int tipoQuarto, boolean wifi, boolean cofre) {
        this.idQuarto = idQuarto;
        this.piso = piso;
        this.idTipoQuarto = idTipoQuarto;
        this.wifi = wifi;
        this.cofre = cofre;
    }

    public void setIdQuarto(int idQuarto) {
        this.idQuarto = idQuarto;
    }

    public void setPiso(int piso) {
        this.piso = piso;
    }

    public void setIdTipoQuarto(int tipoQuarto) {
        this.idTipoQuarto = idTipoQuarto;
    }

    public void setWifi(boolean wifi) {
        this.wifi = wifi;
    }

    public void setCofre(boolean cofre) {
        this.cofre = cofre;
    }

    public int getIdQuarto() {
        return idQuarto;
    }

    public int getPiso() {
        return piso;
    }

    public int getIdTipoQuarto() {
        return idTipoQuarto;
    }

    public boolean isWifi() {
        return wifi;
    }

    public boolean isCofre() {
        return cofre;
    }
}
