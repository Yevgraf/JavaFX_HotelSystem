package DAL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CheckInDAL {
    // method to mark a quarto as checked-in
    public void checkInQuarto(int quartoId) {
        PreparedStatement ps;
        try {
            DBconn dbConn = new DBconn();
            Connection connection = dbConn.getConn();
            ps = connection.prepareStatement("UPDATE Quarto SET ativo = ? WHERE id = ?");
            ps.setBoolean(1, true);
            ps.setInt(2, quartoId);
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    // method to mark a quarto as checked-out
    public void checkOutQuarto(int quartoId) {
        PreparedStatement ps;
        try {
            DBconn dbConn = new DBconn();
            Connection connection = dbConn.getConn();
            ps = connection.prepareStatement("UPDATE Quarto SET ativo = ? WHERE id = ?");
            ps.setBoolean(1, false);
            ps.setInt(2, quartoId);
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}
