package DAL;

import Model.TipoUtilizador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TipoUtilizadorDAL {

    public TipoUtilizador getByNome(String nome) throws SQLException {
        PreparedStatement ps;
        DBconn dbConn = new DBconn();
        Connection connection = dbConn.getConn();
        ps = connection.prepareStatement(
                "SELECT * FROM TipoUtilizador WHERE nome=?",
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY);
        ps.setString(1, nome);
        ResultSet result = ps.executeQuery();
        if (result.next()) {
            if (result.first()) {
                return new TipoUtilizador(
                        result.getInt("Id"),
                        result.getString("Nome"));
            }
        }
        return null;
    }
}
