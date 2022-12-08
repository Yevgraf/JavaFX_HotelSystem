package DAL;

import Model.TipoUtilizador;
import Model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAL {
    public User Login(String utilizador, String password) throws SQLException {
        PreparedStatement ps;
        DBconn dbConn = new DBconn();
        Connection connection = dbConn.getConn();
        ps = connection.prepareStatement(
                "SELECT u.id as uId, u.nome as uNome, nif, morada, dataNascimento, email, contacto, utilizador, palavrapasse, tu.id as tuId, tu.nome as tuNome " +
                        "FROM Utilizador u " +
                        "INNER JOIN TipoUtilizador tu ON tu.Id = u.idTipoUtilizador " +
                        "WHERE u.utilizador = ? " +
                        "AND u.palavrapasse = ?",
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY);
        ps.setString(1, utilizador);

        ps.setString(2, password);
        ResultSet result = ps.executeQuery();

        if (result.next()) {
            if (result.first()) {
                return new User(
                        result.getInt("uId"),
                        result.getString("uNome"),
                        result.getString("nif"),
                        result.getString("morada"),
                        result.getDate("dataNascimento"),
                        result.getString("email"),
                        result.getString("contacto"),
                        result.getString("utilizador"),
                        new TipoUtilizador(
                                result.getInt("tuId"),
                                result.getString("tuNome")
                        )
                );
            }
        }

        return null;
    }
}
