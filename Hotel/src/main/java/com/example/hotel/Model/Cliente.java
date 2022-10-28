package com.example.hotel.Model;


/*import com.example.hotel.DataBase.DataBase;*/

import java.sql.Connection;

public class Cliente {
    private int idCliente;
    private String name;
    private String contacto;
    private String email;
    private String utilizador;
    private String password;


    public Cliente(){

    }
    public Cliente(int idCliente, String name, String contacto, String email, String utilizador, String password) {
        this.idCliente = idCliente;
        this.name = name;
        this.contacto = contacto;
        this.email = email;
        this.utilizador = utilizador;
        this.password = password;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUtilizador(String utilizador) {
        this.utilizador = utilizador;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public String getName() {
        return name;
    }

    public String getContacto() {
        return contacto;
    }

    public String getEmail() {
        return email;
    }

    public String getUtilizador() {
        return utilizador;
    }

    public String getPassword() {
        return password;
    }


}