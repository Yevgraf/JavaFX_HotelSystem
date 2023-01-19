package DAL;

import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CheckoutDAL {


    public void addCheckout(Checkout checkout) throws SQLException {
        DBconn dbConn = new DBconn();
        Connection connection = dbConn.getConn();

        PreparedStatement ps = connection.prepareStatement("INSERT INTO Checkout (reservaId, preco, metodoPagamento, checkout_date_time) VALUES (?, ?, ?, ?)");
        ps.setInt(1, checkout.getIdReserva());
        ps.setBigDecimal(2, BigDecimal.valueOf(checkout.getPreco()));
        ps.setString(3, checkout.getMetodoPagamento());
        ps.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));

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


    public void voltaNaoConsumiveisAoStock(int idReserva) throws SQLException {
        EntradaStockDAL entradaStockDAL = new EntradaStockDAL();
        ObservableList<Stock> lista = FXCollections.observableArrayList();
        try {
            DBconn dbConn = new DBconn();
            Connection connection = dbConn.getConn();
            String cmd = "SELECT p.id AS idProduto, pr.quantidade as quantidade " +
                    "FROM ProdutoReserva pr " +
                    "INNER JOIN Reserva r ON pr.idReserva = r.id " +
                    "INNER JOIN Produto p ON pr.idProduto = p.id " +
                    "WHERE r.id = ? AND p.consumivel = 0;";
            PreparedStatement ps = connection.prepareStatement(cmd);
            ps.setInt(1, idReserva);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Stock obj = new Stock(rs.getString("idProduto"), rs.getInt("quantidade"));
                lista.add(obj);
            }
            entradaStockDAL.updateStock(lista, connection);
            ps.close();
        } catch (Exception ex) {
        }
    }

}

