package DAL;

import Model.Checkout;
import Model.Pagamento;
import Model.Reserva;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CheckoutDAL {


    public void addCheckout(Checkout checkout) throws SQLException {
        DBconn dbConn = new DBconn();
        Connection connection = dbConn.getConn();

        PreparedStatement ps = connection.prepareStatement("INSERT INTO Checkout (reservaId, preco, metodoPagamento) VALUES (?, ?, ?)");
        ps.setInt(1, checkout.getIdReserva());
        ps.setBigDecimal(2, BigDecimal.valueOf(checkout.getPreco()));
        ps.setString(3, checkout.getMetodoPagamento());

        ps.executeUpdate();
    }
    public ObservableList<Pagamento> getPagamentos() throws SQLException {
        ObservableList<Pagamento> pagamentos = FXCollections.observableArrayList();

        DBconn dbConn = new DBconn();
        Connection connection = dbConn.getConn();

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM Pagamento");

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String metodoPagamento = resultSet.getString("metodoPagamento");

            Pagamento pagamento = new Pagamento(id, metodoPagamento);
            pagamentos.add(pagamento);
        }

        return pagamentos;
    }
    public List<Reserva> getCheckedInReservations() throws SQLException {
        List<Reserva> pendingReservations = new ArrayList<>();

        DBconn dbConn = new DBconn();
        Connection connection = dbConn.getConn();

        PreparedStatement ps = connection.prepareStatement("SELECT R.* FROM Reserva R INNER JOIN EstadoReserva ER ON R.id = ER.reserva WHERE ER.estado = 'checkin'");
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

    public void updateReservationStateCheckout(int reservationId, String estado) throws SQLException {
        PreparedStatement ps = null;
        Connection connection = null;
        try {
            DBconn dbConn = new DBconn();
            connection = dbConn.getConn();
            ps = connection.prepareStatement("UPDATE EstadoReserva SET estado = ? WHERE reserva = ?");
            ps.setString(1, estado);
            ps.setInt(2, reservationId);
            ps.executeUpdate();
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

}
