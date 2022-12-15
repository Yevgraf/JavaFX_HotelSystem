package DAL;

import Model.TipoUtilizador;
import Model.Utilizador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class UtilizadorDAL {
    public Utilizador Login(String utilizador, String password) throws SQLException {
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
                return new Utilizador(
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

    public PreparedStatement CriarUtilizador(int id, String nome, String nif, String morada, Date dataNascimento, String email, String contacto, String utilizador, String password, TipoUtilizador tipoUser) throws SQLException {

        PreparedStatement ps;
        DBconn dbConn = new DBconn();
        Connection connection = dbConn.getConn();

        ps = connection.prepareStatement("INSERT INTO Utilizador (nome,nif,morada,dataNascimento,email,contacto,utilizador,palavrapasse,idTipoUtilizador) values (?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1,nome);
        ps.setString(2,nif);
        ps.setString(3,morada);
        ps.setDate(4, (java.sql.Date) dataNascimento);
        ps.setString(5,email);
        ps.setString(6,contacto);
        ps.setString(7,utilizador);
        ps.setString(8,password);
        ps.setInt(9, tipoUser.getId());
        ps.executeUpdate();

        return ps;

    }
}
