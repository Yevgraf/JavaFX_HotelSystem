package DataBase;
import java.sql.*;

public class ConnectionBD {


   private static String dbURL = "jdbc:sqlserver://ctespbd.dei.isep.ipp.pt;databaseName=2022_F_LP3_G4;TrustServerCertificate=True";
    private static String user = "2022_F_LP3_G4";
    private static String pass = "123+qwe*123";

    public static Connection getConn() {
        Connection conn = null;
        try {
            //Class.forName(db_driver).newInstance();
            conn= DriverManager.getConnection(dbURL,user,pass);
        }

        catch (SQLException ex)           {
            System.err.println(ex.getMessage());
        }

        return conn;
    }

}
