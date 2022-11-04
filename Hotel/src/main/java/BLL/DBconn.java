package BLL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBconn {

    private String url ="jdbc:sqlserver://ctespbd.dei.isep.ipp.pt;databaseName=2022_F_LP3_G4;TrustServerCertificate=True";
    private String user = "2022_F_LP3_G4";
    private String pass = "123+qwe*123";

    public void TestConnection() {
        try {
            Connection connection = DriverManager.getConnection(url, user, pass);
            System.out.println("Connected!");
        } catch (SQLException e) {
            System.out.println("Connection Failed!");
            e.printStackTrace();
        }
}
}
