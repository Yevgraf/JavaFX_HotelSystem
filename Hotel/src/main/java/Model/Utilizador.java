package Model;

import DAL.DBconn;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;

public class Utilizador {

    private int id;
    private String nome;
    private String nif;
    private String morada;
    private Date dataNascimento;
    private String email;
    private String contacto;
    private String utilizador;
    private String password;
    private TipoUtilizador tipoUser;

    public Utilizador(
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

    public static ObservableList<Utilizador> getClientes() {
        ObservableList<Utilizador> clientes = FXCollections.observableArrayList();

        try {
            String cmd = "SELECT * FROM Utilizador INNER JOIN TipoUtilizador TU on " +
                    "Utilizador.idTipoUtilizador = TU.id WHERE idTipoUtilizador = '3' ";

            Statement st = DBconn.getConn().createStatement();

            ResultSet rs = st.executeQuery(cmd);

            while (rs.next()) {
                Utilizador obj = new Utilizador(rs.getInt("id"), rs.getString("nome"), rs.getString("nif"),
                        rs.getString("morada"), rs.getDate("dataNascimento"), rs.getString("email"),
                        rs.getString("contato"), rs.getString("utilizador"),
                        new TipoUtilizador(
                                rs.getInt("tuId"),
                                rs.getString("tuNome")));
                clientes.add(obj);
            }

            st.close();
        } catch (Exception ex) {
            MessageBoxes.ShowMessage(Alert.AlertType.ERROR, "Não encontrado", "Erro");
        }

        return clientes;
    }

    public static ObservableList<Utilizador> getColaboradores() {
        ObservableList<Utilizador> colaboradores = FXCollections.observableArrayList();

        try {
            String cmd = "SELECT * FROM Utilizador INNER JOIN TipoUtilizador TU on " +
                    "Utilizador.idTipoUtilizador = TU.id WHERE idTipoUtilizador = '1' AND idTipoUtilizador = '2'";

            Statement st = DBconn.getConn().createStatement();

            ResultSet rs = st.executeQuery(cmd);

            while (rs.next()) {
                Utilizador obj = new Utilizador(rs.getInt("id"), rs.getString("nome"), rs.getString("nif"),
                        rs.getString("morada"), rs.getDate("dataNascimento"), rs.getString("email"),
                        rs.getString("contato"), rs.getString("utilizador"),
                        new TipoUtilizador(
                                rs.getInt("tuId"),
                                rs.getString("tuNome")));
                colaboradores.add(obj);
            }

            st.close();
        } catch (Exception ex) {
            MessageBoxes.ShowMessage(Alert.AlertType.ERROR, "Não encontrado", "Erro");
        }

        return colaboradores;
    }

}
