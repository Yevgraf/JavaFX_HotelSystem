package DAL;

import Model.Reserva;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ReservaDAL {

        public void addReserva(Reserva reserva) throws SQLException {
            PreparedStatement ps2;
            try {
                DBconn dbConn = new DBconn();
                Connection connection = dbConn.getConn();
                ps2 = connection.prepareStatement("INSERT INTO Reserva(nifCliente, idColaborador, idQuarto," +
                        "dataInicio, dataFim, servExtra, preco)" +
                        "VALUES (?,?,?,?,?,?,?)", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

                ps2.setInt(1, reserva.getNifCliente());
                ps2.setInt(2, reserva.getIdColaborador());
                ps2.setInt(3, reserva.getIdQuarto());
                ps2.setString(4, reserva.getDataInicio());
                ps2.setString(5, reserva.getDataFim());
                ps2.setString(6, reserva.getServExtra());
                ps2.setDouble(7, reserva.getPreco());
                ps2.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
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

                reserva = new Reserva(rs.getInt("id"), rs.getInt("nifCliente"), rs.getInt("idColaborador"),
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
                Reserva reserva = new Reserva(rs.getInt("id"), rs.getInt("nifCliente"),
                        rs.getInt("idColaborador"), rs.getInt("idQuarto"),
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
}
