package DAL;

import Model.Registo;

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
        String sql = "SELECT q.idCartao FROM Reserva r JOIN Quarto q ON r.idQuarto = q.id WHERE r.idCliente = ?";

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



}
