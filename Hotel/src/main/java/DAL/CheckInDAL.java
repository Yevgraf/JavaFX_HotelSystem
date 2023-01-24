package DAL;

import BLL.EntradaStockBLL;
import BLL.ReservaBLL;
import Model.Reserva;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CheckInDAL {

    public List<Reserva> getPendingReservations() throws SQLException {
        List<Reserva> pendingReservations = new ArrayList<>();

        DBconn dbConn = new DBconn();
        Connection connection = dbConn.getConn();

        PreparedStatement ps = connection.prepareStatement("SELECT R.* FROM Reserva R INNER JOIN EstadoReserva ER ON R.id = ER.reserva WHERE ER.estado = 'pendente'");
        ResultSet resultSet = ps.executeQuery();

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            int idCliente = resultSet.getInt("idCliente");
            int idQuarto = resultSet.getInt("idQuarto");
            String dataInicio = String.valueOf(resultSet.getDate("dataInicio"));
            String dataFim = String.valueOf(resultSet.getDate("dataFim"));
            Double preco = resultSet.getDouble("preco");
            Reserva reservation = new Reserva(id, idCliente, idQuarto, dataInicio, dataFim, preco);
            pendingReservations.add(reservation);
        }

        return pendingReservations;
    }

    public void updateStockOnCheckIn(int reservationId) throws SQLException {
        String query = "SELECT idProduto, quantidade FROM ProdutoReserva WHERE idReserva = ?";
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            DBconn dbConn = new DBconn();
            connection = dbConn.getConn();
            ps = connection.prepareStatement(query);
            ps.setInt(1, reservationId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String productId = rs.getString("idProduto");
                int quantity = rs.getInt("quantidade");
                updateStock(productId, quantity, connection);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }
    private void updateStock(String productId, int quantity, Connection connection) throws SQLException {
        String query2 = "UPDATE Stock set quantidade = quantidade - ? WHERE idProduto = ?";
        PreparedStatement ps2 = connection.prepareStatement(query2);
        ps2.setInt(1, quantity);
        ps2.setString(2, productId);
        ps2.executeUpdate();
    }

}
