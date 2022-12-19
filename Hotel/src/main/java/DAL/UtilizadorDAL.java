package DAL;

import Model.MessageBoxes;
import Model.TipoUtilizador;
import Model.Utilizador;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.*;
import java.util.Date;

public class UtilizadorDAL {
    public Utilizador Login(String utilizador, String password) throws SQLException {
        PreparedStatement ps;
        DBconn dbConn = new DBconn();
        Connection connection = dbConn.getConn();
        ps = connection.prepareStatement(
                "SELECT u.id as uId, u.nome as uNome, nif, morada, dataNascimento, email, contacto, utilizador, palavrapasse, tu.id as tuId, tu.nome as tuNome " +
                        "FROM Utilizador u " +
                        "INNER JOIN TipoUtilizador tu ON tu.Id = u.idTipoUtilizador " +
                        "WHERE u.utilizador = ? " +
                        "AND u.palavrapasse = ?",
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY);
        ps.setString(1, utilizador);

        ps.setString(2, password);
        ResultSet result = ps.executeQuery();

        if (result.next()) {
            if (result.first()) {
                return new Utilizador(
                        result.getInt("uId"),
                        result.getString("uNome"),
                        result.getString("nif"),
                        result.getString("morada"),
                        result.getDate("dataNascimento"),
                        result.getString("email"),
                        result.getString("contacto"),
                        result.getString("utilizador"),
                        new TipoUtilizador(
                                result.getInt("tuId"),
                                result.getString("tuNome")
                        )
                );
            }
        }

        return null;
    }


    public static PreparedStatement CriarUtilizador(String nome, String nif, String morada, Date dataNascimento, String email, String contacto, String utilizador, String password, String tipoUser) throws SQLException {
        // Validate input data
        if (nome == null || nif == null || morada == null || dataNascimento == null || email == null || contacto == null || utilizador == null || password == null || tipoUser == null) {
            throw new IllegalArgumentException("All fields are required");
        }

        // Get user type object
        TipoUtilizador userType = TipoUtilizadorDAL.getByNome(tipoUser);
        if (userType == null) {
            throw new IllegalArgumentException("Invalid user type: " + tipoUser);
        }

        DBconn dbConn = new DBconn();
        Connection connection = dbConn.getConn();
        PreparedStatement ps = connection.prepareStatement("INSERT INTO Utilizador (nome, nif, morada, dataNascimento, email, contacto, utilizador, palavrapasse, idTipoUtilizador) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");

        ps.setString(1,nome);
        ps.setString(2,nif);
        ps.setString(3,morada);
        ps.setDate(4, (java.sql.Date) dataNascimento);
        ps.setString(5,email);
        ps.setString(6,contacto);
        ps.setString(7,utilizador);
        ps.setString(8,password);
        ps.setInt(9, userType.getId());
        ps.executeUpdate();

        return ps;

    }


    public static ObservableList<Utilizador> getTodosUtilizadores() {
        ObservableList<Utilizador> utilizadores = FXCollections.observableArrayList();

        try {
            String cmd = "SELECT u.*, tu.id as tuId, tu.nome as tuNome FROM Utilizador u " +
                    "INNER JOIN TipoUtilizador tu ON tu.Id = u.idTipoUtilizador " +
                    "WHERE tu.nome IN ('Gestor', 'Funcionario', 'Cliente') ";

            Statement st = DBconn.getConn().createStatement();

            ResultSet rs = st.executeQuery(cmd);

            while (rs.next()) {
                Utilizador obj = new Utilizador(rs.getInt("id"), rs.getString("nome"), rs.getString("nif"),
                        rs.getString("morada"), rs.getDate("dataNascimento"), rs.getString("email"),
                        rs.getString("contacto"), rs.getString("utilizador"),
                        new TipoUtilizador(
                                rs.getInt("tuId"),
                                rs.getString("tuNome")));
                utilizadores.add(obj);
            }

            st.close();
        } catch (Exception ex) {
            MessageBoxes.ShowMessage(Alert.AlertType.ERROR, "Não encontrado", "Erro");
        }
        return utilizadores;
    }

    public static ObservableList<Utilizador> getGestores() {
        ObservableList<Utilizador> clientes = FXCollections.observableArrayList();

        try {
            String cmd = "SELECT u.*, tu.id as tuId, tu.nome as tuNome FROM Utilizador u " +
                    "INNER JOIN TipoUtilizador tu ON tu.Id = u.idTipoUtilizador " +
                    "WHERE tu.nome IN ('Gestor') ";

            Statement st = DBconn.getConn().createStatement();

            ResultSet rs = st.executeQuery(cmd);

            while (rs.next()) {
                Utilizador obj = new Utilizador(rs.getInt("id"), rs.getString("nome"), rs.getString("nif"),
                        rs.getString("morada"), rs.getDate("dataNascimento"), rs.getString("email"),
                        rs.getString("contacto"), rs.getString("utilizador"),
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

    public static ObservableList<Utilizador> getFuncionario() {
        ObservableList<Utilizador> clientes = FXCollections.observableArrayList();

        try {
            String cmd = "SELECT u.*, tu.id as tuId, tu.nome as tuNome FROM Utilizador u " +
                    "INNER JOIN TipoUtilizador tu ON tu.Id = u.idTipoUtilizador " +
                    "WHERE tu.nome IN ('Funcionario') ";

            Statement st = DBconn.getConn().createStatement();

            ResultSet rs = st.executeQuery(cmd);

            while (rs.next()) {
                Utilizador obj = new Utilizador(rs.getInt("id"), rs.getString("nome"), rs.getString("nif"),
                        rs.getString("morada"), rs.getDate("dataNascimento"), rs.getString("email"),
                        rs.getString("contacto"), rs.getString("utilizador"),
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

    public static ObservableList<Utilizador> getClientes() {
        ObservableList<Utilizador> clientes = FXCollections.observableArrayList();

        try {
            String cmd = "SELECT u.*, tu.id as tuId, tu.nome as tuNome FROM Utilizador u " +
                    "INNER JOIN TipoUtilizador tu ON tu.Id = u.idTipoUtilizador " +
                    "WHERE tu.nome IN ('Cliente') ";

            Statement st = DBconn.getConn().createStatement();

            ResultSet rs = st.executeQuery(cmd);

            while (rs.next()) {
                Utilizador obj = new Utilizador(rs.getInt("id"), rs.getString("nome"), rs.getString("nif"),
                        rs.getString("morada"), rs.getDate("dataNascimento"), rs.getString("email"),
                        rs.getString("contacto"), rs.getString("utilizador"),
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
}
