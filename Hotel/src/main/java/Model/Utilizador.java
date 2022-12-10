package Model;

import DAL.DBconn;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;

public class Utilizador{

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
            String nome,
            String nif,
            String morada,
            Date dataNascimento,
            String email,
            String contacto,
            String utilizador,
            String password,
            String tipoUser) {
        this.nome = nome;
        this.nif = nif;
        this.morada = morada;
        this.dataNascimento = dataNascimento;
        this.email = email;
        this.contacto = contacto;
        this.utilizador = utilizador;
        this.password = password;
        this.tipoUser = new TipoUtilizador(0, tipoUser);
    }

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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getMorada() {
        return morada;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getUtilizador() {
        return utilizador;
    }

    public void setUtilizador(String utilizador) {
        this.utilizador = utilizador;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public TipoUtilizador getTipoUser() {
        return tipoUser;
    }

    public void setTipoUser(TipoUtilizador tipoUser) {
        this.tipoUser = tipoUser;
    }

    public static ObservableList<Utilizador> getClientes() {
        ObservableList<Utilizador> clientes = FXCollections.observableArrayList();

        try {
            String cmd = "SELECT * FROM Utilizador WHERE idTipoUtilizador = '3'";

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
            String cmd = "SELECT u.*, tu.id as tuId, tu.nome as tuNome FROM Utilizador u " +
                        "INNER JOIN TipoUtilizador tu ON tu.Id = u.idTipoUtilizador " +
                        "WHERE tu.nome IN ('Gestor', 'Funcionario') ";

            Statement st = DBconn.getConn().createStatement();

            ResultSet rs = st.executeQuery(cmd);

            while (rs.next()) {
                Utilizador obj = new Utilizador(rs.getInt("id"), rs.getString("nome"), rs.getString("nif"),
                        rs.getString("morada"), rs.getDate("dataNascimento"), rs.getString("email"),
                        rs.getString("contacto"), rs.getString("utilizador"),
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

    @Override
    public String toString() {
        return "Utilizador{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", nif='" + nif + '\'' +
                ", morada='" + morada + '\'' +
                ", dataNascimento=" + dataNascimento +
                ", email='" + email + '\'' +
                ", contacto='" + contacto + '\'' +
                ", utilizador='" + utilizador + '\'' +
                ", password='" + password + '\'' +
                ", tipoUser=" + tipoUser +
                '}';
    }
}
