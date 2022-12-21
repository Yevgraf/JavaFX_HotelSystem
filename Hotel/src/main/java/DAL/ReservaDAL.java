package DAL;

import Model.Reserva;

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
            ps.setInt(1, reserva.getId());
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

                reserva = new Reserva(rs.getInt("id"), rs.getInt("nifCliente"),
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
                Reserva reserva = new Reserva(rs.getInt("id"), rs.getInt("nifCliente"), rs.getInt("idQuarto"),
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
        String sql = "UPDATE Reserva SET nifCliente = ?, idQuarto = ?, dataInicio = ?, dataFim = ?, servExtra = ?, preco = ? WHERE id = ?";

        try (Connection conn = DBconn.getConn();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, reserva.getNifCliente());
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
        String sql = "SELECT p.precoPorUnidade, s.preco, q.preco " +
                "FROM Reserva r " +
                "JOIN ProdutoQuarto pq ON r.idQuarto = pq.idQuarto " +
                "JOIN Servico s ON r.id = s.idReserva " +
                "JOIN Produto p ON pq.idProduto = p.id " +
                "JOIN Quarto q ON pq.idQuarto = q.id " +
                "WHERE r.id = ?";

        double totalAmount = 0;

        try (Connection conn = DBconn.getConn();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, reservationId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    double productPrice = rs.getDouble("precoPorUnidade");
                    double servicePrice = rs.getDouble("preco");
                    double roomPrice = rs.getDouble("preco");
                    totalAmount += productPrice + servicePrice + roomPrice;
                }
            }
        }

        return totalAmount;
    }


}
