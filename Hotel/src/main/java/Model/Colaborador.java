package Model;

import java.util.Date;

public class Colaborador {
    private int idColaborador;
    private String nome;
    private String nif;
    private String morada;
    private Date dataNascimento;
    private  String email;

    private String contacto;
    private String utilizador;
    private String password;

    private String tipoColaborador;
    private int idCartao;

    public Colaborador(){

    }

    public Colaborador(int idColaborador, String nome, String nif, String morada, Date dataNascimento, String email, String contacto, String utilizador, String password, String tipoColaborador, int idCartao) {
        this.idColaborador = idColaborador;
        this.nome = nome;
        this.nif = nif;
        this.morada = morada;
        this.dataNascimento = dataNascimento;
        this.email = email;
        this.contacto = contacto;
        this.utilizador = utilizador;
        this.password = password;
        this.tipoColaborador = tipoColaborador;
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

    public String getContacto() {
        return contacto;
    }

    public String getTipoColaborador() {
        return tipoColaborador;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public void setTipoColaborador(String tipoColaborador) {
        this.tipoColaborador = tipoColaborador;
    }
}
