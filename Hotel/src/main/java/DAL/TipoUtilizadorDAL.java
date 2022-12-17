package DAL;

import Model.TipoUtilizador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TipoUtilizadorDAL {
    public static TipoUtilizador getByNome(String nome) throws SQLException {
        if (nome == null) {
            throw new IllegalArgumentException("Nome cannot be null");
        }

        DBconn dbConn = new DBconn();
        Connection connection = dbConn.getConn();
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            ps = connection.prepareStatement("SELECT * FROM TipoUtilizador WHERE nome=?");
            ps.setString(1, nome);
            result = ps.executeQuery();
            if (result.next()) {
                return new TipoUtilizador(
                        result.getInt("Id"),
                        result.getString("Nome"));
            }
        } catch (SQLException e) {
            // Handle exception
        } finally {
            if (result != null) {
                result.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return null;
    }
}
