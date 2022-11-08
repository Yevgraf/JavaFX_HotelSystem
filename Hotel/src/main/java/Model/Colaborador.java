package Model;

import BLL.DBconn;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.*;
import java.util.Date;

import static Model.MessageBoxes.ShowMessage;

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

    public static ObservableList<Colaborador> getColaborador() {
        ObservableList<Colaborador> listaColaborador = FXCollections.observableArrayList();

        try {
            String cmd = "SELECT * FROM Colaborador";


            Statement st = DBconn.getConn().createStatement();

            ResultSet rs = st.executeQuery(cmd);

            while (rs.next()) {
                Colaborador obj = new Colaborador(rs.getInt("idColaborador"), rs.getString("nome"), rs.getString("email"),
                        rs.getString("morada"), rs.getDate("dataNascimento"), rs.getString("contacto"),rs.getString("nif"),
                        rs.getString("password"),rs.getString("tipoColaborador"),rs.getString("utilizador"),rs.getInt("idCartao"));
                listaColaborador.add(obj);
            }

            st.close();
        } catch (Exception ex) {
            System.err.println("Erro: " + ex.getMessage());
        }
        return listaColaborador;
    }
    public static void deleteColaborador(int idColaborador) throws SQLException {
        Connection conn = DBconn.getConn();
        String cmd = "";

        try {

            cmd = "DELETE FROM Colaborador "
                    + "WHERE idColaborador=?";

            PreparedStatement statement = conn.prepareStatement(cmd);
            statement.setInt(1, idColaborador);

            //Execute the update
            statement.executeUpdate();

            //commit in case you have turned autocommit to false
            conn.commit();
        } catch (SQLException ex) {
            System.err.println("Erro: " + ex.getMessage());
            if (ex.getErrorCode() == 547) {
                ShowMessage(Alert.AlertType.ERROR, "Registo não pode ser eliminado pois é usado noutra tabela", "Falhou a eliminação do registo!");
            } else {
                ShowMessage(Alert.AlertType.ERROR, ex.getMessage(), "Falhou a eliminação do registo!");
            }
        }
    }
    public static void saveMarca(Integer idColaborador, String nome, String nif, String morada, Date dataNascimento, String email, String contacto, String utilizador, String password, String tipoColaborador, int idCartao) throws SQLException {
        Connection conn = DBconn.getConn();
        String cmd = "";

        try {
            if ( idColaborador == null) {
                //Novo
                cmd = "INSERT INTO Colaborador(idColaborador, nome, nif, morada, dataNascimento, email, contacto, utilizador, password, tipoColaborador, idCartao) VALUES (NEWIDCOLABORADOR(),?,?,?,?,?,?,?,?,?,?)";

            } else {
                //Update
                cmd = "UPDATE Colaborador SET "
                        + " idColaborador=?,"
                        + " nome=?,"
                        + " nif=?,"
                        + " morada=?,"
                        + " dataNascimento=?,"
                        + " email=?,"
                        + " contacto=?,"
                        + " utilizador=?,"
                        + " password=?,"
                        + " tipoColaborador=?,"
                        + " idCartao=?,"
                        + "WHERE idColaborador='" + idColaborador + "'";

            }

            PreparedStatement statement = conn.prepareStatement(cmd);
            statement.setInt(1,idColaborador);
            statement.setString(2,nome);
            statement.setString(3, nif);
            statement.setString(4, morada);
            statement.setDate(5, (java.sql.Date) dataNascimento);
            statement.setString(6, email);
            statement.setString(7, contacto);
            statement.setString(8, utilizador);
            statement.setString(9, password);
            statement.setString(10, tipoColaborador);
            statement.setInt(11, idCartao);
            //Execute the update
            statement.executeUpdate();

            //commit in case you have turned autocommit to false
            // conn.commit();
        } catch (SQLIntegrityConstraintViolationException ex) {
            System.err.println("Erro: " + ex.getMessage());
            ShowMessage(Alert.AlertType.ERROR, "Registo Duplicado", "Falhou ao guardar registo!");
        } catch (SQLException ex) {
            System.err.println("Erro: " + ex.getMessage());
            ShowMessage(Alert.AlertType.ERROR, "", "Falhou ao guardar registo!");
        }
    }
    public static Colaborador getColaboradorById(Integer idColaborador) {
        Connection conn = DBconn.getConn();
        String cmd;
        Colaborador obj = new Colaborador();
        try {
            cmd = "SELECT * FROM Colaborador WHERE idColaborador=?";

            PreparedStatement statement = conn.prepareStatement(cmd);
            statement.setInt(1, idColaborador);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                obj = new Colaborador(rs.getInt("idColaborador"), rs.getString("nome"), rs.getString("email"),
                        rs.getString("dataNascimento"), rs.getDate("morada"), rs.getString("contacto"),rs.getString("nif"),
                        rs.getString("password"),rs.getString("tipoColaborador"),rs.getString("utilizador"),rs.getInt("idCartao"));

            }

            statement.close();
        } catch (Exception ex) {
            System.err.println("Erro: " + ex.getMessage());
        }
        return obj;
    }

    @Override
    public String toString() {
        return this.nome;
    }
}
