package Model;

import java.util.Date;

public class User {

    private int id;
    private String nome;
    private String nif;
    private String morada;
    private Date dataNascimento;
    private  String email;
    private String contacto;
    private String utilizador;
    private String password;
    private TipoUtilizador tipoUser;

    public User(
            int id,
            String nome,
            String nif,
            String morada,
            Date dataNascimento,
            String email,
            String contacto,
            String utilizador,
            TipoUtilizador tipoUser) {
        this.id = id;
        this.nome = nome;
        this.nif = nif;
        this.morada = morada;
        this.dataNascimento = dataNascimento;
        this.email = email;
        this.contacto = contacto;
        this.utilizador = utilizador;
        this.tipoUser = tipoUser;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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


    public String getContacto() {
        return contacto;
    }

    public TipoUtilizador getTipoUser() {
        return tipoUser;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public void setTipoUser(TipoUtilizador tipoColaborador) {
        this.tipoUser = tipoColaborador;
    }

}
