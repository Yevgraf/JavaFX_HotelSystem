package com.example.hotel.Model;

import java.util.Date;

public class Colaborador {
    private int idColaborador;
    private String nome;
    private String nif;
    private String morada;
    private Date dataNascimento;
    private  String email;
    private String utilizador;
    private String password;
    private int idCartao;

    public Colaborador(){

    }
    public Colaborador(int idColaborador, String nome, String nif, String morada, Date dataNascimento, String email, String utilizador, String password, int idCartao) {
        this.idColaborador = idColaborador;
        this.nome = nome;
        this.nif = nif;
        this.morada = morada;
        this.dataNascimento = dataNascimento;
        this.email = email;
        this.utilizador = utilizador;
        this.password = password;
        this.idCartao = idCartao;
    }

    public void setIdColaborador(int idColaborador) {
        this.idColaborador = idColaborador;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
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

    public void setIdCartao(int idCartao) {
        this.idCartao = idCartao;
    }

    public int getIdColaborador() {
        return idColaborador;
    }

    public String getNome() {
        return nome;
    }

    public String getNif() {
        return nif;
    }

    public String getMorada() {
        return morada;
    }

    public Date getDataNascimento() {
        return dataNascimento;
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

    public int getIdCartao() {
        return idCartao;
    }
}
