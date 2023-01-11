package DAL;

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
                addServiceToReservation(reservationId, "Quarto");
            }
            reserva.setId(reservationId);
            return reserva;
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

    private void addServiceToReservation(int reservationId, String service) throws SQLException {
        PreparedStatement ps = null;
        try {
            DBconn dbConn = new DBconn();
            Connection connection = dbConn.getConn();


            ps = connection.prepareStatement("SELECT id FROM Servico WHERE servico = ?");
            ps.setString(1, service);
            ResultSet rs = ps.executeQuery();
            int serviceId = -1;
            if (rs.next()) {
                serviceId = rs.getInt(1);
            }
            ps.close();


            ps = connection.prepareStatement("INSERT INTO ServicoReserva(idReserva, idServico) VALUES (?, ?)");
            ps.setInt(1, reservationId);
            ps.setInt(2, serviceId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (ps != null) {
                ps.close();
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

    public static void deleteReservation(int reservationId) throws SQLException {
        deleteServicoReservaForReservation(reservationId);
        deleteEstadoReserva(reservationId);
        deleteEstadoReservaForReservation(reservationId);
        deleteReservationById(reservationId);
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

    /*public double getTotalProdutosReserva(int reservationId) throws SQLException {
        double total = 0;

        String sql = "SELECT SUM(pq.quantidade * p.precoPorUnidade) AS total FROM Reserva r INNER JOIN Quarto q ON r.idQuarto = q.id INNER JOIN ProdutoReserva pq ON q.id = pq.idQuarto INNER JOIN Produto p ON pq.idProduto = p.id WHERE r.id = ?";

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
    }*/


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
                if (estado.equals("checkin")) {
                    MessageBoxes.ShowMessage(Alert.AlertType.WARNING, "Reserva não pode ser apagada, já se encontra em checkin!", "Reserva iniciada");
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
                cmd = "UPDATE EstadoReserva SET estado = 'cancelada' WHERE reserva = ?";
                ps = connection.prepareStatement(cmd);
                ps.setInt(1, reservationId);
                ps.executeUpdate();
                ps.close();
                MessageBoxes.ShowMessage(Alert.AlertType.INFORMATION, "Reserva cancelada!", "Sucesso:");
            }
        }
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
