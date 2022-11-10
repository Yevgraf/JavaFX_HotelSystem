package Model;

import BLL.DBconn;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.Date;

public class Colaborador {
    private String nome;
    private String nif;
    private String morada;
    private Date dataNascimento;
    private  String email;

    private String contacto;
    private String utilizador;
    private String password;

    private String tipoColaborador;

    private int idCartaoColaborador;

    public Colaborador(){

    }

    public Colaborador(String nome, String nif, String morada, java.sql.Date dataNascimento, String email, String contacto, String utilizador, String palavrapasse, String tipoColaborador) {
        this.nome = nome;
        this.nif = nif;
        this.morada = morada;
        this.dataNascimento = dataNascimento;
        this.email = email;
        this.contacto = contacto;
        this.utilizador = utilizador;
        this.password = palavrapasse;
        this.tipoColaborador = tipoColaborador;
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

    public void setIdCartaoColaborador(int idCartaoColaborador) {
        this.idCartaoColaborador = idCartaoColaborador;
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

    public int getIdCartaoColaborador() {
        return idCartaoColaborador;
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

    public static ObservableList<Colaborador> getColaborador() {
        ObservableList<Colaborador> lista = FXCollections.observableArrayList();

        try {
            String cmd = "SELECT * FROM Colaborador";

            Statement st = DBconn.getConn().createStatement();

            ResultSet rs = st.executeQuery(cmd);

            while (rs.next()) {
                Colaborador obj = new Colaborador(rs.getString("nome"), rs.getString("nif"), rs.getString("morada"),rs.getDate("dataNascimento"),rs.getString("email"),
                rs.getString("contacto"),rs.getString("utilizador"),rs.getString("palavrapasse"), rs.getString("tipoColaborador"));
                lista.add(obj);
            }

            st.close();
        } catch (Exception ex) {
            System.err.println("Erro: " + ex.getMessage());
        }
        return lista;
    }
    @Override
    public String toString() {
        return this.nome;
    }


}
