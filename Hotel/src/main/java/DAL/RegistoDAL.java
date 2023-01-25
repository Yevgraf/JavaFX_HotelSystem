package DAL;

import BLL.UtilizadorPreferences;
import Model.Registo;
import Model.Servico;
import com.example.hotel.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RegistoDAL {




    public List<Registo> getAllRegistos() throws SQLException {
        List<Registo> registos = new ArrayList<>();

        DBconn dbConn = new DBconn();
        Connection connection = dbConn.getConn();

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM Registo");

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            int idCartao = resultSet.getInt("idCartao");
            int idCliente = resultSet.getInt("idCliente");
            String Local = resultSet.getString("Local");
            Timestamp Data = resultSet.getTimestamp("Data");

            Registo registo = new Registo(id, idCartao, idCliente, Local, Data);
            registos.add(registo);
        }

        return registos;
    }

    public void addRegisto(Registo registo) throws SQLException {
        DBconn dbConn = new DBconn();
        Connection connection = dbConn.getConn();

        PreparedStatement ps = connection.prepareStatement("INSERT INTO Registo (idCartao, idCliente, Local, Data) VALUES (?, ?, ?, ?)");
        ps.setInt(1, registo.getIdCartao());
        ps.setInt(2, registo.getIdCliente());
        ps.setString(3, registo.getLocal());
        ps.setTimestamp(4, registo.getData());

        ps.executeUpdate();
    }


    public static int getCardIdByClientId(int clientId) throws SQLException {
        String sql = "SELECT q.idCartao FROM Reserva r "
                + "JOIN EstadoReserva er ON er.reserva = r.id "
                + "JOIN Quarto q ON q.id = r.idQuarto "
                + "WHERE r.idCliente = ? AND er.estado = 'checkin'";

        DBconn dbConn = new DBconn();
        Connection connection = dbConn.getConn();

        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setInt(1, clientId);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            return rs.getInt("idCartao");
        }
        return -1;
    }


    public static ObservableList<Registo> getRegistosByCartaoId(int idCartao) throws SQLException {
        String cmd = "SELECT * FROM Registo WHERE idCartao = ?";
        DBconn dbconn = new DBconn();
        Connection connection = dbconn.getConn();
        PreparedStatement ps = connection.prepareStatement(cmd);
        ps.setInt(1, idCartao);
        ResultSet rs = ps.executeQuery();
        ObservableList<Registo> registos = FXCollections.observableArrayList();
        while(rs.next()){
            Registo registo = new Registo(rs.getInt("id"), rs.getInt("idCartao"),
                    rs.getInt("idCliente"), rs.getString("local"), rs.getTimestamp("data"));
            registos.add(registo);
        }
        return registos;
    }


    public static ObservableList<Registo> getRegistosByLocal(String local) throws SQLException {
        String cmd = "SELECT * FROM Registo WHERE local = ?";
        DBconn dbconn = new DBconn();
        Connection connection = dbconn.getConn();
        PreparedStatement ps = connection.prepareStatement(cmd);
        ps.setString(1, local);
        ResultSet rs = ps.executeQuery();
        ObservableList<Registo> registos = FXCollections.observableArrayList();
        while(rs.next()){
            Registo registo = new Registo(rs.getInt("id"), rs.getInt("idCartao"),
                    rs.getInt("idCliente"), rs.getString("local"), rs.getTimestamp("data"));
            registos.add(registo);
        }
        return registos;
    }

}
