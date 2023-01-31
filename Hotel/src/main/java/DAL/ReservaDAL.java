package DAL;

import BLL.EstacionamentoBLL;
import Model.EstacionamentoAPI.ResponseTicket;
import Model.EstacionamentoAPI.TicketInfo;
import Model.MessageBoxes;
import Model.Reserva;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReservaDAL {

    public Reserva addReserva(Reserva reserva) throws SQLException {
        PreparedStatement ps = null;
        int reservationId = -1;
        Connection connection = null;
        try {
            DBconn dbConn = new DBconn();
            connection = dbConn.getConn();
            ps = connection.prepareStatement("INSERT INTO Reserva(idCliente, idQuarto," +
                    "dataInicio, dataFim, preco)" +
                    "VALUES (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, reserva.getIdCliente());
            ps.setInt(2, reserva.getIdQuarto());
            ps.setString(3, reserva.getDataInicio());
            ps.setString(4, reserva.getDataFim());
            ps.setDouble(5, reserva.getPreco());
            ps.executeUpdate();
            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                reservationId = generatedKeys.getInt(1);
            }
            if (reservationId > 0) {
                addReservationState(reservationId, "pendente");
                transferProdutosToProdutoReserva(reservationId, reserva.getIdQuarto());
            }
            reserva.setId(reservationId);
            return reserva;
        } catch (SQLException e) {
            MessageBoxes.ShowMessage(Alert.AlertType.ERROR, "Preencher todos os campos!", "Erro:");
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


    private void transferProdutosToProdutoReserva(int reservationId, int roomId) {
        try (Connection connection = DBconn.getConn();
             PreparedStatement psSelect = connection.prepareStatement("SELECT idProduto, quantidade FROM ProdutoQuarto WHERE idQuarto = ?");
             PreparedStatement psInsert = connection.prepareStatement("INSERT INTO ProdutoReserva (idReserva, idProduto, quantidade, idQuarto) VALUES (?,?,?,?)")) {

            psSelect.setInt(1, roomId);
            ResultSet rs = psSelect.executeQuery();

            while (rs.next()) {
                String idProduto = rs.getString("idProduto");
                int quantidade = rs.getInt("quantidade");

                psInsert.setInt(1, reservationId);
                psInsert.setString(2, idProduto);
                psInsert.setInt(3, quantidade);
                psInsert.setInt(4, roomId);
                psInsert.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void addServiceToReservation(int reservationId, String service) throws SQLException {
        PreparedStatement ps = null;
        Connection connection = null;
        try {
            DBconn dbConn = new DBconn();
            connection = dbConn.getConn();

            String checkQuery = "SELECT COUNT(*) FROM Servico WHERE servico = ?";
            ps = connection.prepareStatement(checkQuery);
            ps.setString(1, service);
            ResultSet checkResult = ps.executeQuery();
            if (!checkResult.next() || checkResult.getInt(1) == 0) {
                throw new SQLException("Este serviço não existe");
            }

            String insertQuery = "INSERT INTO ServicoReserva(idReserva, idServico) SELECT ?, id FROM Servico WHERE servico = ?";
            ps = connection.prepareStatement(insertQuery);
            ps.setInt(1, reservationId);
            ps.setString(2, service);
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


    public static ObservableList<Reserva> getReservas() {
        ObservableList<Reserva> reservas = FXCollections.observableArrayList();
        try {
            String cmd = "SELECT * FROM Reserva";
            PreparedStatement ps = DBconn.getConn().prepareStatement(cmd);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Reserva reserva = new Reserva(rs.getInt("id"), rs.getInt("idCliente"), rs.getInt("idQuarto"),
                        rs.getString("dataInicio"), rs.getString("dataFim"),
                        rs.getDouble("preco"));
                reservas.add(reserva);
            }
            ps.close();
        } catch (Exception ex) {
        }
        return reservas;
    }

    public static List<Reserva> getReservasPendentes() {
        List<Reserva> reservas = new ArrayList<>();
        try {
            String cmd = "SELECT r.* FROM Reserva r INNER JOIN EstadoReserva e ON r.id = e.reserva WHERE e.estado = 'pendente' OR e.estado = 'checkin'";
            Statement st = DBconn.getConn().createStatement();
            ResultSet rs = st.executeQuery(cmd);
            while (rs.next()) {
                Reserva reserva = new Reserva(rs.getInt("id"), rs.getInt("idCliente"), rs.getInt("idQuarto"),
                        rs.getString("dataInicio"), rs.getString("dataFim"),
                        rs.getDouble("preco"));
                reservas.add(reserva);
            }
            st.close();
        } catch (Exception ex) {
        }
        return reservas;
    }


    public static boolean isRoomAvailable(int roomId, LocalDate startDate) throws SQLException {
        String query = "SELECT dataInicio, dataFim FROM Reserva WHERE idQuarto = ? And dataInicio = ?";
        try (PreparedStatement stmt = DBconn.getConn().prepareStatement(query)) {
            stmt.setInt(1, roomId);
            stmt.setString(2, startDate.toString());
            try (ResultSet rs = stmt.executeQuery()) {
                return !rs.next();
            }
        }
    }

    public void updateReservationPrice(int reservationId, double newPrice) throws SQLException {
        PreparedStatement ps = null;
        Connection connection = null;
        try {
            DBconn dbConn = new DBconn();
            connection = dbConn.getConn();
            ps = connection.prepareStatement("UPDATE Reserva SET preco = ? WHERE id = ?");
            ps.setDouble(1, newPrice);
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
    public List<Reserva> searchReservationsByClientName(String clientName) throws SQLException {
        PreparedStatement ps = null;
        Connection connection = null;
        ResultSet rs = null;
        List<Reserva> reservations = new ArrayList<>();
        try {
            DBconn dbConn = new DBconn();
            connection = dbConn.getConn();
            ps = connection.prepareStatement("SELECT r.id, r.idCliente, r.idQuarto, r.dataInicio, r.dataFim, r.preco FROM Reserva r JOIN Utilizador u ON r.idCliente = u.id WHERE u.nome LIKE ?");
            ps.setString(1, "%"+clientName+"%");
            rs = ps.executeQuery();
            while (rs.next()) {
                Reserva reservation = new Reserva();
                reservation.setId(rs.getInt("id"));
                reservation.setIdCliente(rs.getInt("idCliente"));
                reservation.setIdQuarto(rs.getInt("idQuarto"));
                reservation.setDataInicio(String.valueOf(rs.getDate("dataInicio")));
                reservation.setDataFim(String.valueOf(rs.getDate("dataFim")));
                reservation.setPreco(rs.getDouble("preco"));
                reservations.add(reservation);
            }
            return reservations;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }


    public static void deleteReservation(int reservationId) throws SQLException {
        String cmd = "SELECT estado FROM EstadoReserva WHERE reserva = ?";
        DBconn dbConn = new DBconn();
        Connection connection = dbConn.getConn();
        PreparedStatement ps = connection.prepareStatement(cmd);
        ps.setInt(1, reservationId);
        ResultSet rs = ps.executeQuery();
        String estado = null;
        if (rs.next()) {
            estado = rs.getString("estado");
        }
        ps.close();
        if (estado == null || estado.equals("checkout") || estado.equals("checkin") || estado.equals("cancelada")) {
            MessageBoxes.ShowMessage(Alert.AlertType.ERROR, "Não é possível apagar esta reserva.", "Erro");
        } else {
            EstacionamentoBLL bll = new EstacionamentoBLL();
            String ticketId = getTicketIdForReservation(reservationId);
            if (ticketId != null) {
                bll.DeleteTicket(ticketId);
            }
            deleteEstadoReservaForReservation(reservationId);
            deleteProdutoReserva(reservationId);
            deleteServicoReservaForReservation(reservationId);
            deleteCheckoutForReservation(reservationId);
            deleteReservationById(reservationId);
            MessageBoxes.ShowMessage(Alert.AlertType.INFORMATION, "Reserva pendente apagada!", "Apagada:");
        }
    }

    private static String getTicketIdForReservation(int reservationId) throws SQLException {
        String cmd = "SELECT ticketID FROM Reserva WHERE id = ?";
        DBconn dbConn = new DBconn();
        Connection connection = dbConn.getConn();
        PreparedStatement ps = connection.prepareStatement(cmd);
        ps.setInt(1, reservationId);
        ResultSet rs = ps.executeQuery();
        String ticketId = null;
        if (rs.next()) {
            ticketId = rs.getString("ticketID");
        }
        ps.close();

        return ticketId;
    }







    private static void deleteProdutoReserva(int reservationId) throws SQLException {

        String cmd = ("DELETE FROM ProdutoReserva WHERE idReserva = ?");
        executeDelete(cmd, reservationId);
    }


    private static void deleteEstadoReservaForReservation(int reservationId) throws SQLException {
        String cmd = "DELETE FROM EstadoReserva WHERE reserva = ?";

        executeDelete(cmd, reservationId);
    }

    private static void deleteServicoReservaForReservation(int reservationId) throws SQLException {
        String cmd = "DELETE FROM ServicoReserva WHERE idReserva = ?";
        executeDelete(cmd, reservationId);
    }

    private static void deleteCheckoutForReservation(int reservationId) throws SQLException {
        String cmd = "DELETE FROM Checkout WHERE reservaId = ?";
        executeDelete(cmd, reservationId);
    }


    private static void deleteReservationById(int reservationId) throws SQLException {
        String cmd = "DELETE FROM Reserva WHERE id = ?";
        executeDelete(cmd, reservationId);
    }

    private static void executeDelete(String cmd, int reservationId) throws SQLException {
        DBconn dbConn = new DBconn();
        Connection connection = dbConn.getConn();
        PreparedStatement ps = connection.prepareStatement(cmd);
        ps.setInt(1, reservationId);
        ps.executeUpdate();
        ps.close();
    }


    public static List<Reserva> getReservasComTicket(int idUtilizador) throws SQLException {
        String cmd = "SELECT * FROM Reserva WHERE idCliente = ? AND ticketID IS NOT NULL";
        DBconn dbconn = new DBconn();
        Connection connection = dbconn.getConn();
        PreparedStatement ps = connection.prepareStatement(cmd);
        ps.setInt(1, idUtilizador);
        ResultSet rs = ps.executeQuery();
        List<Reserva> reservas = new ArrayList<>();
        while (rs.next()) {
            Reserva reserva = new Reserva(rs.getInt("id"), rs.getInt("idCliente"),
                    rs.getInt("idQuarto"), rs.getString("dataInicio"), rs.getString("dataFim"),
                    rs.getDouble("preco"), rs.getObject("ticketID").toString());
            reservas.add(reserva);
        }
        return reservas;
    }


    public void updateReserva(Reserva reserva) throws SQLException {
        String sql = "UPDATE Reserva SET idCliente = ?, idQuarto = ?, dataInicio = ?, dataFim = ?, preco = ? WHERE id = ?";

        try (Connection conn = DBconn.getConn();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, reserva.getIdCliente());
            stmt.setInt(2, reserva.getIdQuarto());
            stmt.setString(3, reserva.getDataInicio());
            stmt.setString(4, reserva.getDataFim());
            stmt.setDouble(5, reserva.getPreco());
            stmt.setInt(6, reserva.getId());
            stmt.executeUpdate();
        }
    }

    public void updateReservaComResponseTicketID(Reserva reserva, ResponseTicket responseTicket) throws SQLException {
        String sql = "UPDATE Reserva SET ticketID = ? WHERE id = ?";

        try (Connection conn = DBconn.getConn();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, responseTicket.TicketId);
            stmt.setInt(2, reserva.getId());
            stmt.executeUpdate();
        }
    }


    public double getTotalServicosReserva(int reservationId) throws SQLException {
        String sql = "SELECT SUM(s.preco) as preco " +
                "FROM Reserva r " +
                "JOIN ServicoReserva sr ON r.id = sr.idReserva " +
                "JOIN Servico s ON s.id = sr.idServico " +
                "WHERE r.id = ?";

        try (Connection conn = DBconn.getConn();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, reservationId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("preco");
                }
            }
        }

        return 0;
    }

    public double getTotalProdutosReserva(int reservationId) throws SQLException {
        double total = 0;

        String sql = "SELECT SUM(pr.quantidade * p.precoParaCliente) AS total " +
                "FROM Reserva r INNER JOIN ProdutoReserva PR on r.id = PR.idReserva " +
                "INNER JOIN Produto P on P.id = PR.idProduto WHERE r.id = ?";

        Connection conn = DBconn.getConn();

        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setInt(1, reservationId);

        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            total = rs.getDouble("total");
        }

        rs.close();
        stmt.close();
        conn.close();

        return total;
    }

    public double getTotalProdutosQuarto(int reservationId) throws SQLException {
        double total = 0;

        String sql = "SELECT SUM(PQ.quantidade * p.precoParaCliente) AS total FROM Reserva r " +
                "INNER JOIN ProdutoQuarto PQ on r.idQuarto = PQ.idQuarto " +
                "INNER JOIN Produto P on P.id = PQ.idProduto WHERE r.id = ?";

        Connection conn = DBconn.getConn();

        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setInt(1, reservationId);

        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            total = rs.getDouble("total");
        }

        rs.close();
        stmt.close();
        conn.close();

        return total;
    }


    public void addReservationState(int reservationId, String estado) throws SQLException {
        PreparedStatement ps = null;
        Connection connection = null;
        try {
            DBconn dbConn = new DBconn();
            connection = dbConn.getConn();
            ps = connection.prepareStatement("INSERT INTO EstadoReserva(reserva, estado) VALUES (?, ?)");
            ps.setInt(1, reservationId);
            ps.setString(2, estado);
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

    public void updateReservationState(int reservationId, String estado) throws SQLException {
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

    public static void deleteEstadoReserva(int reservationId) throws SQLException {
        PreparedStatement ps = null;
        Connection connection = null;
        try {
            DBconn dbConn = new DBconn();
            connection = dbConn.getConn();
            ps = connection.prepareStatement("SELECT estado FROM EstadoReserva WHERE reserva=?");
            ps.setInt(1, reservationId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String estado = rs.getString("estado");
                if (estado.equals("checkin") || estado.equals("cancelada") || estado.equals("checkout")) {
                    MessageBoxes.ShowMessage(Alert.AlertType.WARNING, "Reserva não pode ser apagada, já se encontra em checkin ou cancelada", "Reserva iniciada");
                    return;
                }
            }

            ps = connection.prepareStatement("SELECT estado FROM Reserva WHERE id = ?");
            ps.setInt(1, reservationId);
            rs = ps.executeQuery();
            if (rs.next()) {
                String estado = rs.getString("estado");
                if (estado.equals("cancelada")) {
                    MessageBoxes.ShowMessage(Alert.AlertType.WARNING, "Reserva já se encontra cancelada, não pode ser apagada.", "Reserva cancelada");
                    return;
                }
            }
            ps = connection.prepareStatement("DELETE FROM Checkout WHERE reservaId=?");
            ps.setInt(1, reservationId);
            ps.executeUpdate();

            ps = connection.prepareStatement("DELETE FROM EstadoReserva WHERE reserva=?");
            ps.setInt(1, reservationId);
            ps.executeUpdate();

            ps = connection.prepareStatement("DELETE FROM Reserva WHERE id=?");
            ps.setInt(1, reservationId);
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


    public static ObservableList<Reserva> getReservasByEstadoReserva(String estadoReserva) {
        ObservableList<Reserva> reservas = FXCollections.observableArrayList();
        try {
            String cmd = "SELECT r.* FROM Reserva r INNER JOIN EstadoReserva e ON r.id = e.reserva WHERE e.estado = ?";
            PreparedStatement ps = DBconn.getConn().prepareStatement(cmd);
            ps.setString(1, estadoReserva);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Reserva reserva = new Reserva(rs.getInt("id"), rs.getInt("idCliente"), rs.getInt("idQuarto"),
                        rs.getString("dataInicio"), rs.getString("dataFim"),
                        rs.getDouble("preco"));
                reservas.add(reserva);
            }
            ps.close();
        } catch (Exception ex) {
            System.err.println("Erro: " + ex.getMessage());
        }
        return reservas;
    }


    public void cancelReservation(int reservationId) throws SQLException {
        String cmd = "SELECT estado FROM EstadoReserva WHERE reserva = ?";
        DBconn dbConn = new DBconn();
        Connection connection = dbConn.getConn();
        PreparedStatement ps = connection.prepareStatement(cmd);
        ps.setInt(1, reservationId);
        ResultSet rs = ps.executeQuery();
        String estado = null;
        if (rs.next()) {
            estado = rs.getString("estado");
        }
        ps.close();
        if (estado == null || estado.equals("checkout")) {
            MessageBoxes.ShowMessage(Alert.AlertType.ERROR, "Não é possível cancelar esta reserva.", "Erro");
        } else {
            if (estado == null || estado.equals("cancelada")) {
                MessageBoxes.ShowMessage(Alert.AlertType.ERROR, "A reserva já se encontra cancelada.", "Erro");
            } else {
                if(estado.equals("checkin")){
                    returnProductToStock(reservationId);
                }
                cmd = "UPDATE EstadoReserva SET estado = 'cancelada' WHERE reserva = ?";
                ps = connection.prepareStatement(cmd);
                ps.setInt(1, reservationId);
                ps.executeUpdate();
                ps.close();
                MessageBoxes.ShowMessage(Alert.AlertType.INFORMATION, "Reserva cancelada!", "Sucesso:");
            }
        }
    }


    private void returnProductToStock(int reservationId) throws SQLException {
        DBconn dbConn = new DBconn();
        Connection connection = dbConn.getConn();

        connection.setAutoCommit(false);

        try {
            String query = "SELECT idProduto, quantidade FROM ProdutoReserva WHERE idReserva = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, reservationId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String productId = rs.getString("idProduto");
                int quantity = rs.getInt("quantidade");

                updateStock(productId, quantity, connection);
            }
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    private void updateStock(String productId, int quantity, Connection connection) throws SQLException {
        String query2 = "UPDATE Stock set quantidade = quantidade + ? WHERE idProduto = ?";
        PreparedStatement ps2 = connection.prepareStatement(query2);
        ps2.setInt(1, quantity);
        ps2.setString(2, productId);
        ps2.executeUpdate();
    }


    public List<LocalDate> getDataInicial(int idQuarto) {
        List<LocalDate> datasIniciais = new ArrayList<>();
        try {
            DBconn dbConn = new DBconn();
            Connection connection = dbConn.getConn();
            String cmd = "SELECT r.dataInicio FROM Reserva r " +
                    "INNER JOIN Quarto q on q.id = r.idQuarto " +
                    "INNER JOIN EstadoReserva er ON r.id = er.reserva " +
                    "WHERE q.id = ? AND (er.estado = 'checkin' OR er.estado = 'pendente')";
            PreparedStatement ps = connection.prepareStatement(cmd);
            ps.setInt(1, idQuarto);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                java.sql.Date data = rs.getDate("dataInicio");
                datasIniciais.add(data.toLocalDate());
            }
            ps.close();
            return datasIniciais;
        } catch (Exception ex) {
        }
        return null;
    }

    public List<LocalDate> getDataFinal(int idQuarto) {
        List<LocalDate> datasFinais = new ArrayList<>();
        try {
            DBconn dbConn = new DBconn();
            Connection connection = dbConn.getConn();
            String cmd = "SELECT r.dataFim FROM Reserva r " +
                    "INNER JOIN Quarto q on q.id = r.idQuarto " +
                    "INNER JOIN EstadoReserva er ON r.id = er.reserva " +
                    "WHERE q.id = ? AND (er.estado = 'checkin' OR er.estado = 'pendente')";
            PreparedStatement ps = connection.prepareStatement(cmd);
            ps.setInt(1, idQuarto);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                java.sql.Date data = rs.getDate("dataFim");
                datasFinais.add(data.toLocalDate());
            }
            ps.close();
            return datasFinais;
        } catch (Exception ex) {
        }
        return null;
    }

    public LocalDate getProximaData(int idQuarto, LocalDate ultData) {
        try {
            DBconn dbConn = new DBconn();
            Connection connection = dbConn.getConn();
            String cmd = "SELECT TOP 1 r.dataInicio FROM Reserva r " +
                    "INNER JOIN EstadoReserva er ON r.id = er.reserva " +
                    "WHERE r.dataInicio > ? " +
                    "AND r.idQuarto = ? AND (er.estado = 'checkin' OR er.estado = 'pendente') " +
                    "ORDER BY r.dataInicio ASC";
            PreparedStatement ps = connection.prepareStatement(cmd);
            ps.setDate(1, Date.valueOf((ultData)));
            ps.setInt(2, idQuarto);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                java.sql.Date proxData = rs.getDate("dataInicio");
                return proxData.toLocalDate();
            }
            ps.close();
        } catch (Exception ex) {
        }
        return null;
    }

    public Boolean verificaSeExisteReserva(int idQuarto) {
        try {
            DBconn dbConn = new DBconn();
            Connection connection = dbConn.getConn();
            String cmd = "SELECT r.id FROM Reserva r " +
                    "INNER JOIN Quarto q on q.id = r.idQuarto " +
                    "WHERE q.id = ?";
            PreparedStatement ps = connection.prepareStatement(cmd);
            ps.setInt(1, idQuarto);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ps.close();
                return true;
            } else {
                ps.close();
                return false;
            }
        } catch (Exception ex) {
        }
        return null;
    }
}
