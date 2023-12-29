package DAL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBconn {

    private static String url = "";
    private static String user = "";
    private static String pass = "";

    /**
     * A função getConn serve para fazer a conexão á base de dados
     *
     * @return devolve a ligação da base de dados
     */
    public static Connection getConn() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, pass);
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return conn;
    }
}
