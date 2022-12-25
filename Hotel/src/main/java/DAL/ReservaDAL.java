package DAL;

import Model.MessageBoxes;
import Model.Reserva;
import javafx.scene.control.Alert;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReservaDAL {

    public int addReserva(Reserva reserva) throws SQLException {
        PreparedStatement ps = null;
        int reservationId = -1; // Initialize the reservation ID to -1
        Connection connection = null;
        try {
            DBconn dbConn = new DBconn();
            connection = dbConn.getConn();
            ps = connection.prepareStatement("INSERT INTO Reserva(idCliente, idQuarto," +
                    "dataInicio, dataFim, servExtra, preco)" +
                    "VALUES (?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, reserva.getIdCliente());
            ps.setInt(2, reserva.getIdQuarto());
            ps.setString(3, reserva.getDataInicio());
            ps.setString(4, reserva.getDataFim());
            ps.setString(5, reserva.getServExtra());
            ps.setDouble(6, reserva.getPreco());
            ps.executeUpdate();
            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                reservationId = generatedKeys.getInt(1);
            }

            if (reservationId > 0) {
                // Add the reservation to the ReservationState table with the initial state "pending"
                addReservationState(reservationId, "pendente");
            }

            return reservationId;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (ps != null) {
                ps.close(); // Close the Prepared Statement to avoid any leaks
            }
            if (connection != null) {
                connection.close(); // Close the connection to the database
            }
        }
    }



    public Reserva getReservaByQuartoId(int quartoId) {
        Reserva reserva = null;
        PreparedStatement ps;
        try {
            DBconn dbConn = new DBconn();
            Connection connection = dbConn.getConn();
            ps = connection.prepareStatement("SELECT * FROM Reserva WHERE idQuarto = ?");
            ps.setInt(1, quartoId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                String dataInicio = dateFormat.format(rs.getDate("dataInicio"));
                String dataFim = dateFormat.format(rs.getDate("dataFim"));

                reserva = new Reserva(rs.getInt("id"), rs.getInt("idCliente"),
                        rs.getInt("idQuarto"), dataInicio, dataFim, rs.getString("servExtra"), rs.getDouble("preco"));

            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return reserva;
    }


    public static List<Reserva> getReservas() {
        List<Reserva> reservas = new ArrayList<>();
        try {
            String cmd = "SELECT * FROM Reserva";
            Statement st = DBconn.getConn().createStatement();
            ResultSet rs = st.executeQuery(cmd);
            while (rs.next()) {
                Reserva reserva = new Reserva(rs.getInt("id"), rs.getInt("idCliente"), rs.getInt("idQuarto"),
                        rs.getString("dataInicio"), rs.getString("dataFim"),
                        rs.getString("servExtra"), rs.getDouble("preco"));
                reservas.add(reserva);
            }
            st.close();
        } catch (Exception ex) {
            System.err.println("Erro: " + ex.getMessage());
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
        PreparedStatement ps2;
        try {
            deleteEstadoReserva(reservationId);

            DBconn dbConn = new DBconn();
            Connection connection = dbConn.getConn();

            ps2 = connection.prepareStatement("DELETE FROM Reserva WHERE id = ?");
            ps2.setInt(1, reservationId);
            ps2.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void updateReserva(Reserva reserva) throws SQLException {
        String sql = "UPDATE Reserva SET idCliente = ?, idQuarto = ?, dataInicio = ?, dataFim = ?, servExtra = ?, preco = ? WHERE id = ?";

        try (Connection conn = DBconn.getConn();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, reserva.getIdCliente());
            stmt.setInt(2, reserva.getIdQuarto());
            stmt.setString(3, reserva.getDataInicio());
            stmt.setString(4, reserva.getDataFim());
            stmt.setString(5, reserva.getServExtra());
            stmt.setDouble(6, reserva.getPreco());
            stmt.setInt(7, reserva.getId());
            stmt.executeUpdate();
        }
    }


    public double calculateTotalAmount(Integer reservationId) throws SQLException {
        String sql = "SELECT SUM(p.precoPorUnidade * pq.quantidade) as productAmount, " +
                "SUM(s.preco) as serviceAmount, SUM(q.preco) as roomPrice " +
                "FROM Reserva r " +
                "JOIN ProdutoQuarto pq ON r.idQuarto = pq.idQuarto " +
                "JOIN Servico s ON r.id = s.id " +
                "JOIN Produto p ON pq.idProduto = p.id " +
                "JOIN Quarto q ON pq.idQuarto = q.id " +
                "WHERE r.id = ?";


        double totalAmount = 0;

        try (Connection conn = DBconn.getConn();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, reservationId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    double productAmount = rs.getDouble("productAmount");
                    double serviceAmount = rs.getDouble("serviceAmount");
                    double roomPrice = rs.getDouble("roomPrice");
                    totalAmount = productAmount + serviceAmount + roomPrice;
                }
            }
        }

        return totalAmount;
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
            ps = connection.prepareStatement("DELETE FROM EstadoReserva WHERE reserva=? AND state<>'checkin'");
            ps.setInt(1, reservationId);
            ps.executeUpdate();
        } catch (SQLException e) {
            MessageBoxes.ShowMessage(Alert.AlertType.WARNING,"Reserva não pode ser apagada, já se encontra em checkin!", "Reserva iniciada");
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
