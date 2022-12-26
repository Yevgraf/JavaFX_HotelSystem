package DAL;

import BLL.ReservaBLL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CheckInDAL {
    public void checkIn(int reservationId) throws SQLException {

      ReservaDAL dal = new ReservaDAL();
      dal.updateReservationState(reservationId, "checkin");


        PreparedStatement ps = null;
        Connection connection = null;
        try {
            DBconn dbConn = new DBconn();
            connection = dbConn.getConn();
            ps = connection.prepareStatement("UPDATE Quarto SET ativo = 1 WHERE id = (SELECT idQuarto FROM Reserva WHERE id = ?)");
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

}
